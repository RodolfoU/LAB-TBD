from flask import jsonify,request, make_response
from common import *
from controller import app, general
from model import *

@app.route('/api/parallelByBSP/personasPorReunion/')
def select_personasPorReunionBSP():
    if myId == primaryId:
        data = request.json
        urlBase = "http://ADDR/api/parallelByBSP/slave/personasPorReunion/"
        responses = general.send_to_all("requests.get",servers,nbrServers,urlBase,data)
        table = general.union_responses_list(responses)
        tableSort = general.sort_byCol(table,["codreu","nombre"],False)

        return {"resp":tableSort }
    else:
        return "deje ejecutar en el nodo primary"


@app.route('/api/parallelByBSP/slave/personasPorReunion/',methods=['GET'])
def select_slavePersonasPorReunionBSP():

    urlBase = "http://ADDR/api/parallelByBSP/slave/personasPorReunion-paso1/"
    responses = general.send_to_all("requests.get",servers,nbrServers,urlBase,None)
    tableSalas = general.union_responses_list(responses)

    urlBase = "http://ADDR/api/parallelByBSP/slave/personasPorReunion-paso2/"
    responses = general.send_to_all("requests.get",servers,nbrServers,urlBase,tableSalas)
    tableReuSal = general.union_responses_list(responses)

    urlBase = "http://ADDR/api/parallelByBSP/slave/personasPorReunion-paso3/"
    responses = general.send_to_one("requests.get",servers[myId],urlBase,tableReuSal)
    tableReuSalPer = general.union_responses_list(responses)

    print([myId,tableReuSalPer])

    return {"resp" : tableReuSalPer, "server":myId }


@app.route('/api/parallelByBSP/slave/personasPorReunion-paso1/',methods=['GET'])
def select_slavePersonasPorReunionBSP_paso1():

    tempTable = select_data("SELECT a.codsala, a.descrip descripsala, a.codcom "
                            +" FROM sala a",dbConnConfig)

    return {"resp" : tempTable, "server":myId }

@app.route('/api/parallelByBSP/slave/personasPorReunion-paso2/',methods=['GET'])
def select_slavePersonasPorReunionBSP_paso2():

    tempTable1=request.json
    txtIn = get_txtInByCol(tempTable1, "codsala")

    print(["select_slavePersonasPorReunionBSP_paso2",txtIn])
    tempTable2 = select_data("select a.codreu, a.descripc descripreu, a.codsala "
                             +" from reunion a where a.codsala in ("+txtIn+")",dbConnConfig)

    resp = general.join_tables(tempTable1,tempTable2,"codsala")

    return {"resp" : resp, "server":myId }


@app.route('/api/parallelByBSP/slave/personasPorReunion-paso3/',methods=['GET'])
def select_slavePersonasPorReunionBSP_paso3():

    tempTable1=request.json
    txtIn = get_txtInByCol(tempTable1, "codreu")

    print(["select_slavePersonasPorReunionBSP_paso3",txtIn])

    tempTable2 = select_data("SELECT a.rut, a.nombre, b.codreu"
                             +" FROM persona a, invitado b"
                             +" where"
                             +" a.rut=b.rut and"
                             +" b.flgasiste=2"
                             +" and b.codreu in ("+txtIn+")",dbConnConfig)

    resp = general.join_tables(tempTable1,tempTable2,"codreu")

    return {"resp" : resp, "server":myId }


#############################################################################################

@app.route('/api/parallelByBSP/reunionesConAsistentes/')
def select_reunionesConAsistentesBSP():
    if myId == primaryId:
        data = request.json
        urlBase = "http://ADDR/api/parallelByBSP/slave/reunionesConAsistentes/"
        responses = general.send_to_all("requests.get",servers,nbrServers,urlBase,data)
        table = general.union_responses_list(responses)
        tableSort = general.sort_byCol(table,["codreu","descripreu"],False)

        return {"resp":tableSort }
    else:
        return "deje ejecutar en el nodo primary"


@app.route('/api/parallelByBSP/slave/reunionesConAsistentes/',methods=['GET'])
def select_slaveReunionesConAsistentesBSP():

    urlBase = "http://ADDR/api/parallelByBSP/slave/reunionesConAsistentes-paso1/"
    responses = general.send_to_all("requests.get",servers,nbrServers,urlBase,None)
    tableInvitados = general.union_responses_list(responses)
    tableInvitadosGroup = general.group_by(tableInvitados,["codreu"])

    urlBase = "http://ADDR/api/parallelByBSP/slave/reunionesConAsistentes-paso2/"
    responses = general.send_to_one("requests.get",servers[myId],urlBase,tableInvitadosGroup)
    tableReuniones = general.union_responses_list(responses)

    return {"resp" : tableReuniones, "server":myId }


@app.route('/api/parallelByBSP/slave/reunionesConAsistentes-paso1/',methods=['GET'])
def select_slaveReunionesConAsistentesBSP_paso1():

    tempTable = select_data("select a.codreu"
                            +" from invitado a"
                            +" where a.flgasiste=2"
                            +" group by a.codreu",dbConnConfig)

    return {"resp" : tempTable, "server":myId }


@app.route('/api/parallelByBSP/slave/reunionesConAsistentes-paso2/',methods=['GET'])
def select_slaveReunionesConAsistentesBSP_paso2():

    tempTable1=request.json
    txtIn = get_txtInByCol(tempTable1, "codreu")

    tempTable2 = select_data("select a.codreu, a.descripc descripreu "
                             +" from reunion a where a.codreu in("+txtIn+")"
                             +" group by a.codreu, a.descripc",dbConnConfig)

    return {"resp" : tempTable2, "server":myId }

@app.route('/api/parallelByBSP/reunionesConAsistentes2/')
def select_reunionesConAsistentesBSP2():
    if myId == primaryId:
        data = request.json
        urlBase = "http://ADDR/api/parallelByBSP/slave/reunionesConAsistentes2/"
        responses = general.send_to_all("requests.get",servers,nbrServers,urlBase,data)
        table = general.union_responses_list(responses)
        tableGroup = general.group_by(table,["codreu","descripreu"])
        tableSort = general.sort_byCol(tableGroup,["codreu","descripreu"],False)

        return {"resp":tableSort }
    else:
        return "deje ejecutar en el nodo primary"

@app.route('/api/parallelByBSP/slave/reunionesConAsistentes2/',methods=['GET'])
def select_slaveReunionesConAsistentesBSP2():

    urlBase = "http://ADDR/api/parallelByBSP/slave/reunionesConAsistentes-paso1/"
    responses = general.send_to_one("requests.get",myServer,urlBase,None)
    tableInvitados = general.union_responses_list(responses)

    urlBase = "http://ADDR/api/parallelByBSP/slave/reunionesConAsistentes-paso2/"
    responses = general.distribute_table("requests.get",servers,nbrServers,urlBase,tableInvitados,"reunion")
    tableReuniones = general.union_responses_list(responses)

    return {"resp" : tableReuniones, "server":myId }