from flask import request, jsonify
import requests
from common import *
from controller import app, general
from model import *


@app.route('/api/parallelInsert/<table>/',methods=['POST'])
def ctrl_create(table):
    if myId == primaryId:
        data = request.json
        des = hashFunc(table,data,nbrServers)
        urlBase = "http://ADDR/api/parallelInsert/slave/"+table+"/"
        res = general.send_to_one("requests.post",servers[des],urlBase, data)
        if res[0].ok:
            return res[0].json()
        else:
            return res[0]
    else:
        return "deje ejecutar en el nodo primary"


@app.route('/api/parallelInsert/slave/<table>/',methods=['POST'])
def ctrl_slaveCreate(table):
    data = request.json
    resp = insert_row(dbConnConfig,table, data)
    return {"resp" : [resp], "server":myId }