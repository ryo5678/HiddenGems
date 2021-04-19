import os                 # os is used to get environment variables IP & PORT
from flask import Flask   # Flask is the web app that we will customize
from flask import render_template
from flask import request


app = Flask(__name__)     # create an app

@app.route('/notes')
def get_notes():
    notes = {1:{'title':'First note','text':'This is my first note','date':'10-1-2020'},
             2:{'title':'Second note','text':'This is my second note','date':'10-2-2020'},
             3:{'title':'Third note','text':'This is my third note','date':'10-3-2020'}
             }
    a_user = {'name': 'Swapnil Katakwar', 'email': 'mogli@uncc.edu'}
    return render_template('notes.html', user=a_user, notes=notes)

@app.route('/notes/new', methods=['GET', 'POST'])
def new_note():
    a_user = {'name': 'Swapnil Katakwar', 'email': 'mogli@uncc.edu'}
    print('request method is',request.method)
    if request.method == 'POST':
        request_data = request.form
        return f"data: {request_data}  !"
    else:
        return render_template('new.html', user=a_user)


@app.route('/notes/<note_id>')
def get_note(note_id):
    notes = {1:{'title':'First note','text':'This is my first note','date':'10-1-2020'},
             2:{'title':'Second note','text':'This is my second note','date':'10-2-2020'}
             }
    a_user = {'name': 'Swapnil Katakwar', 'email': 'mogli@uncc.edu'}
    return render_template('note.html',note=notes[int(note_id)], user=a_user)
@app.route('/')
@app.route('/index')
def index():
 a_user = {'name':'Swapnil Katakwar','email':'mogli@uncc.edu'}

 return render_template('index.html', user =a_user)

app.run(host=os.getenv('IP', '127.0.0.1'),port=int(os.getenv('PORT', 5000)),debug=True)
