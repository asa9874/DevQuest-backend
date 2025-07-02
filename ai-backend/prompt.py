import google.generativeai as genai
from env import API_KEY
import json
import re

def generate_quest(task_input):
    prompt = make_quest_prompt(task_input)
    genai.configure(api_key=API_KEY)
    model = genai.GenerativeModel('gemini-1.5-flash')
    response = model.generate_content(prompt)
    try:
        parsed = json.loads(response.text)
        return parsed
    except Exception:
        return response.text

def make_quest_prompt(task_input):
    return f"""
    너는 개발자 자기개발 퀘스트를 생성하는 AI야.
    아래 입력을 참고해서 퀘스트의 이름과 설명을 JSON 형태로 만들어줘.

    규칙:
    - 반드시 JSON만 반환해. 불필요한 설명, 인사, 코드블록 없이 오직 JSON만 출력해.
    - JSON의 키는 \"name\", \"description\", \"success\" 세 개만 사용해.
    - 입력이 개발자 자기개발과 관련 없는 부적절한 내용(예: 욕설, 음란, 폭력, 광고, 의미 없는 입력 등)이면 name과 description은 빈 문자열로, success는 \"fail\"로 반환해.
    - 정상적인 경우에는 name과 description을 생성하고, success는 \"success\"로 반환해.
    - \"name\"에는 입력 주제에 맞는 퀘스트 이름을 간결하게 작성해.
    - \"description\"에는 해당 퀘스트의 목적이나 학습/수행 방법을 1~2문장으로 설명해.

    입력: {task_input}
    출력:
    """

def generate_quiz(task_input):
    prompt = make_quiz_prompt(task_input)
    genai.configure(api_key=API_KEY)
    model = genai.GenerativeModel('gemini-1.5-flash')
    response = model.generate_content(prompt)
    try:
        parsed = json.loads(response.text)
        return parsed
    except Exception:
        return response.text

def make_quiz_prompt(task_input):
    return f"""
    너는 개발자 학습용 퀴즈를 생성하는 AI야.
    아래 입력을 참고해서 4지선다 객관식 퀴즈를 만들어줘.

    규칙:
    - 반드시 JSON만 반환해. 불필요한 설명, 인사, 코드블록 없이 오직 JSON만 출력해.
    - JSON의 키는 \"title\", \"question\", \"option1\", \"option2\", \"option3\", \"option4\", \"answer\", \"success\" 8개만 사용해.
    - 각 옵션은 보기 내용만 작성하고, answer는 정답 옵션 번호(1~4)로 반환해.
    - title은 퀴즈의 주제를 간단히 요약해.
    - question은 실제 문제를 작성해.
    - 입력이 부적절하거나 개발과 무관하면 모든 필드는 빈 문자열 또는 0, answer는 0, success는 \"fail\"로 하고, 정상적인 경우에는 success는 \"success\"로 반환해.

    입력: {task_input}
    출력:
    """

def clean_llm_json(text):
    text = text.strip()
    # 코드블록 제거
    if text.startswith('```'):
        text = text.lstrip('`').split('\n', 1)[-1]
    if text.endswith('```'):
        text = text.rstrip('`').rsplit('\n', 1)[0]
    # 앞뒤 공백/개행 제거
    text = text.strip()
    return text

def generate_quiz_batch(name, description, number):
    prompt = make_quiz_batch_prompt(name, description, number)
    genai.configure(api_key=API_KEY)
    model = genai.GenerativeModel('gemini-1.5-flash')
    response = model.generate_content(prompt)
    cleaned = clean_llm_json(response.text)
    try:
        parsed = json.loads(cleaned)
        return parsed
    except Exception:
        return cleaned

def make_quiz_batch_prompt(name, description, number):
    return f"""
    너는 개발자 학습용 퀴즈를 여러 개 생성하는 AI야.
    아래 입력을 참고해서 4지선다 객관식 퀴즈를 {number}개 만들어줘.

    규칙:
    - 반드시 각 퀴즈를 JSON 객체로 반환하고, 전체는 JSON 배열로 반환해. 불필요한 설명, 인사, 코드블록 없이 오직 JSON만 출력해.
    - 각 퀴즈의 JSON 키는 \"title\", \"question\", \"option1\", \"option2\", \"option3\", \"option4\", \"answer\", \"success\" 8개만 사용해.
    - 각 옵션은 보기 내용만 작성하고, answer는 정답 옵션 번호(1~4)로 반환해.
    - title은 퀴즈의 주제를 간단히 요약해.
    - question은 실제 문제를 작성해.
    - 입력이 부적절하거나 개발과 무관하면 모든 필드는 빈 문자열 또는 0, answer는 0, success는 \"fail\"로 하고, 정상적인 경우에는 success는 \"success\"로 반환해.

    입력:\n- name: {name}\n- description: {description}\n출력:
    """

def make_guild_search_prompt(user_query, guilds, top_n=3):
    guild_list = "\n".join([
        f"{i+1}. name: {g['name']}, description: {g['description']}" for i, g in enumerate(guilds)
    ])
    return f"""
    아래는 여러 길드의 목록이야.
    입력: {user_query}
    목록:
    {guild_list}
    입력과 가장 관련 있는 길드 {top_n}개를 name, description만 포함된 JSON 배열로 반환해. 불필요한 설명, 인사, 코드블록 없이 오직 JSON만 출력해.
    """

def generate_guild_search_result(user_query, guilds, top_n=3):
    prompt = make_guild_search_prompt(user_query, guilds, top_n)
    import google.generativeai as genai
    from env import API_KEY
    import json
    genai.configure(api_key=API_KEY)
    model = genai.GenerativeModel('gemini-1.5-flash')
    response = model.generate_content(prompt)
    text = response.text.strip()
    if text.startswith('```'):
        text = text.lstrip('`').split('\n', 1)[-1]
    if text.endswith('```'):
        text = text.rstrip('`').rsplit('\n', 1)[0]
    text = text.strip()
    try:
        return json.loads(text)
    except Exception:
        return text

if __name__ == "__main__":
    task_input = input("입력:")
    previous_tasks = [
    ]
    result = generate_quest(task_input, previous_tasks)
    print(json.dumps(result, ensure_ascii=False, indent=2))