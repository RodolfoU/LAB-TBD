import requests
import threading
import copy
import time
from operator import itemgetter
from common import *



def run_http_thread(http_function,server,urlBase,data,_id,_resp):
    addr = getAddr(server["host"], server["port"])
    _url = getUrl(urlBase,addr)

    #txt  = "AQUI:"+_url+" , "+str(data)
    #print(txt)
    r = eval(http_function)(url = _url , json = data)
    _resp[_id] = r
    return


def send_to_one(http_function,server,urlBase, data):
    addr = getAddr(server["host"], server["port"])
    url = getUrl(urlBase,addr)
    res = eval(http_function)(url, json=data)
    return [res]



def send_to_all(http_function,servers,nbrServers,urlBase,params):
    threads = []
    respThreads=[""]*nbrServers
    for i in range(nbrServers):
        process = threading.Thread(target=run_http_thread, args=(http_function,servers[i],urlBase,params,i,respThreads))
        threads.append(process)
        process.start()

    for process in threads:
        process.join()

    return respThreads


def distribute_table(http_function,servers,nbrServers,urlBase,table,tableJoinName):

    tables={}
    for i,row in enumerate(table):
        des = hashFunc(tableJoinName,row,nbrServers)
        v = tables.get(des,-1)
        if v==-1:
            tables[des]=[row]
        else:
            tables[des].append(row)

    #print(["distribute_table", tables ])

    threads = []
    respThreads=[""]*len(tables)
    for k,tableAux in tables.items():
            process = threading.Thread(target=run_http_thread, args=(http_function,servers[k],urlBase,tableAux,k,respThreads))
            threads.append(process)
            process.start()

    for process in threads:
        process.join()

    return respThreads

def union_responses(responses):
    resp=[]
    for response in responses:
        if response.ok:
            resp.append(response.json())

    return resp


def union_responses_list(responses):
    resp=[]
    for response in responses:
        jresp = response.json()
        if response.ok and len(jresp["resp"])>0:
            resp =resp+jresp["resp"]

    return resp


def create_index(table,colName):
    idx={}

    for i,row in enumerate(table):
        v = row[colName]
        if idx.get(v,-1)==-1:
            idx[v]=[i]
        else:
            idx[v].append(i)

    return idx


def union_rows(rowA,rowB):
    data = copy.deepcopy(rowA)
    for k, v in rowB.items():
        data[k]=v
    return data


def join_tables(tableA,tableB,colIdx):
    idxA = create_index(tableA,colIdx)
    idxB = create_index(tableB,colIdx)
    resp=[]
    for k, vA in idxA.items():

        vB = idxB.get(k,-1)
        if vB != -1:
            for i in vA:
                for j in vB:
                    resp.append( union_rows(tableA[i],tableB[j]) )
    return resp

def make_idByRow(row,cols):
    id=""
    data={}
    for k,v in row.items():
        if k in cols:
            id=id+v+"#_"
            data[k]=v
    return id, data

def group_by(table,cols):
    find={}
    resp=[]
    for row in table:
        id,data = make_idByRow(row,cols)
        if find.get(id,-1)==-1:
            find[id]=1
            resp.append(data)

    return resp

def sort_byCol(table,cols,flgDesc):
    return sorted(table, key = itemgetter(*cols),reverse=flgDesc)