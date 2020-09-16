from flask import Flask
from common import *


def create_app():
    _app = Flask(__name__)
    return _app


#CONSTANTE DEL SISTEMA
app = create_app()

@app.route('/')
def welcome():
    return "Consultas sobre Base de Datos Distribuida => myId ="+str(myId)+" ,primaryId="+str(primaryId)

import general
import parallelInsert
import parallelDelete
import parallelByPrimary
import parallelByBSP


def run_app():
    app.run(myHost,myPort,debug=True, threaded=True)