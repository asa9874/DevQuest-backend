from flask import Flask, request, jsonify
from flask_cors import CORS
import json
from prompt import generate_quest, generate_quiz

app = Flask(__name__)
CORS(app)

@app.route('/api/quest', methods=['POST'])
def create_quest():
    data = request.json
    task_input = data.get('task_input', '')
    result = generate_quest(task_input)
    return jsonify(result)

@app.route('/api/quiz', methods=['POST'])
def create_quiz():
    data = request.json
    task_input = data.get('task_input', '')
    result = generate_quiz(task_input)
    return jsonify(result)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
