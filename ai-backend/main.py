from flask import Flask, request, jsonify
from flask_cors import CORS
import json
import pymysql
from prompt import generate_quest, generate_quiz, make_quiz_batch_prompt, generate_quiz_batch, generate_guild_search_result

app = Flask(__name__)
CORS(app)

@app.route('/api/quest', methods=['POST'])
def create_quest():
    print("퀘스트ai")
    data = request.json
    task_input = data.get('task_input', '')
    result = generate_quest(task_input)
    return jsonify(result)

@app.route('/api/quiz', methods=['POST'])
def create_quiz():
    print("퀴즈ai")
    data = request.json
    task_input = data.get('task_input', '')
    result = generate_quiz(task_input)
    return jsonify(result)

@app.route('/api/quiz/batch', methods=['POST'])
def create_quiz_batch():
    print("퀴즈배치ai")
    data = request.json
    name = data.get('name', '')
    description = data.get('description', '')
    number = int(data.get('number', 1))
    if number > 10:
        number = 10
    result = generate_quiz_batch(name, description, number)
    return jsonify(result)

@app.route('/api/guild/search', methods=['GET'])
def search_guild():
    print("길드검색")
    keyword = request.args.get('query', '')
    conn = pymysql.connect(
        host='mysql', 
        port=3306,     
        user='devquest',
        password='devquest1234',
        db='devquest',
        charset='utf8mb4',
        cursorclass=pymysql.cursors.DictCursor
    )
    try:
        with conn.cursor() as cursor:
            sql = """
                SELECT g.id, g.name, g.description, m.name as leader_name 
                FROM guild g
                JOIN member m ON g.member_id = m.id
                LIMIT 30
            """
            cursor.execute(sql)
            guilds = cursor.fetchall()
    finally:
        conn.close()
    result = generate_guild_search_result(keyword, guilds, top_n=5)
    return jsonify(result)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
