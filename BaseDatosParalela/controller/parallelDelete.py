from flask import jsonify,request, make_response
from common import *
from controller import app, general
from model import *


@app.route('/api/parallelDelete/<table>/all/',methods=['DELETE'])
def ctrl_delete(table):
    if myId == primaryId:
        data = request.json

        urlBase = "http://ADDR/api/parallelDelete/slave/"+table+"/all/"

        responses = general.send_to_all("requests.delete",servers,nbrServers,urlBase,data)

        return {"resp":general.union_responses(responses)}

    else:
        return "debe ejecutar en el nodo primary"


@app.route('/api/parallelDelete/slave/<table>/all/',methods=['DELETE'])
def ctrl_slaveDelete(table):
    #data = request.json
    resp = delete_table(table,dbConnConfig)
    return {"resp" : resp, "server":myId }
