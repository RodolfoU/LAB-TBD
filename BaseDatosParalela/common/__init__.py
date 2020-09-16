import json
import sys

###############################################################################
current_milli_time = lambda: int(round(time.time() * 1000))

###############################################################################
def hashFunc(tableName,data, nbrServers):
    value=0
    if tableName=="voluntario":
        value=data["id"]

    elif tableName=="vol_habilidad":
        value=data["id"]

    elif tableName=="habilidad":
        value=data["id"]

    return int(value) % nbrServers

###############################################################################
def getUrl(urlBase, addr):
    return urlBase.replace("ADDR", addr)

###############################################################################
def getAddr(host, port):
    return host+":"+str(port)

###############################################################################
def get_txtInByCol(table, col):
    txtIn=""
    find={}
    for row in table:
        if find.get(row[col],-1)==-1:
            find[ row[col] ] = 1
            txtIn=txtIn + row[col] +","


    if txtIn=="":
        return "-1"
    else:
        l = len(txtIn)
        return txtIn[0:l-1]

###############################################################################
def iniConfig():
    print 'Cantidad de argumentos:', len(sys.argv)
    print 'Lista de argumentos:', str(sys.argv)

    if len(sys.argv) < 2:
        print("error, debe ingresar el nombre del archivo de configuracion")
        sys.exit(1);

    configFile = sys.argv[1]


    data = ""
    with open(configFile) as json_file:
        data = json.load(json_file)

    if data == "":
        print("error, cargue correctamente el json de configuracion")
        sys.exit(1);

    _myId=data["myId"]
    _primaryId=data["primaryId"]
    _dbConnConfig=data["dbConnConfig"]
    _servers=data["servers"]
    _myHost=_servers[_myId]["host"]
    _myPort=_servers[_myId]["port"]
    _myAddr=getAddr(_myHost,_myPort)
    _myServer=_servers[_myId]
    _nbrServers = len(_servers)

    return _myId,_primaryId,_dbConnConfig,_servers,_myHost,_myPort,_myAddr,_myServer,_nbrServers

###############################################################################
#CONSTANTES DEL SISTEMA
myId,primaryId,dbConnConfig,servers,myHost,myPort,myAddr,myServer,nbrServers = iniConfig()