from flask import jsonify,request, make_response
from common import *
from controller import app, general
from model import *

###################################################################

@app.route('/api/parallelByPrimary/voluntariosPorId/')
def select_voluntariosPorId():
    if myId == primaryId:
        data = request.json

        urlBase = "http://ADDR/api/parallelByPrimary/slave/voluntariosPorId-paso1/"
        responses = general.send_to_all("requests.get",servers,nbrServers,urlBase,data)
        tmpTableVoluntarios = general.union_responses_list(responses)
        return {"resp":tmpTableVoluntarios}
    else:
        return "deje ejecutar en el nodo primary"

@app.route('/api/parallelByPrimary/slave/voluntariosPorId-paso1/',methods=['GET'])
def select_slaveVoluntariosPorId():
    resp = select_data("select v.id from voluntario v",dbConnConfig)
    print (["select_slaveVoluntarios",myId,resp])
    return {"resp" : resp, "server":myId }


##################################################################################################

@app.route('/api/parallelByPrimary/voluntariosPorHabilidad/')
def select_voluntariosPorHabilidad():
    if myId == primaryId:
        data = request.json
        urlBase = "http://ADDR/api/parallelByPrimary/slave/voluntariosPorId-paso1/"
        responses = general.send_to_all("requests.get",servers,nbrServers,urlBase,data)
        tmpTableReuniones = general.union_responses_list(responses)

        urlBase = "http://ADDR/api/parallelByPrimary/slave/voluntariosPorHabilidad/"
        responses = general.distribute_table("requests.get",servers,nbrServers,urlBase,tmpTableReuniones,"id_voluntario")
        tmpTableSalas = general.union_responses_list(responses)

        return {"resp":tmpTableSalas}
    else:
        return "deje ejecutar en el nodo primary"

@app.route('/api/parallelByPrimary/slave/voluntariosPorHabilidad/',methods=['GET'])
def select_slavevoluntariosPorHabilidad():
    tempTable1=request.json
    txtIn = get_txtInByCol(tempTable1, "id")
    print(["select_slavevoluntariosPorHabilidad",txtIn])
    tempTable2= select_data("SELECT v.nombre"
                            +" FROM vol_habilidad vh, voluntario v"
                            +" where vh.id_voluntario = v.id",dbConnConfig)

    resp=general.join_tables(tempTable1,tempTable2,"id_voluntario")

    return {"resp" : resp, "server":myId }