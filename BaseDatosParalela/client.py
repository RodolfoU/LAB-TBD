import random
import requests
import time
import datetime

###############################################################################
current_milli_time = lambda: int(round(time.time() * 1000))


###############################################################################
def run_http_func(http_func,action, url, data=None):
    res = eval(http_func)(url, json=data)
    if res.ok:
        print ([action,data,"ok", res.json()])
        #for row in res.json()["resp"]:
         #   print(row)
    else:
        print ([action,data,"error",0])

###############################################################################
def create_vol_habilidad(amount):
    for i in range(amount):
        data = {"id":i, "id_voluntario": i, "id_habilidad": i}
        run_http_func("requests.post","insert_vol_habilidad", 'http://localhost:5050/api/parallelInsert/vol_habilidad/',data)

###############################################################################
def create_habilidad(amount):
    for i in range(amount):
        data = {"id":i, "descrip": "descrip_"+str(i)}
        run_http_func("requests.post","insert_habilidad", 'http://localhost:5050/api/parallelInsert/habilidad/',data)

###############################################################################
def create_voluntario(amount):
    for i in range(amount):
        data = {"id":i, "nombre": "voluntario_"+str(i), "fnacimiento": "1999-05-12"}
        run_http_func("requests.post","insert_voluntario", 'http://localhost:5050/api/parallelInsert/voluntario/',data)


###############################################################################
def delete_tablas():
    run_http_func("requests.delete","delete_voluntario", 'http://localhost:5050/api/parallelDelete/voluntario/all/',None)
    run_http_func("requests.delete","delete_habilidad", 'http://localhost:5050/api/parallelDelete/habilidad/all/',None)
    run_http_func("requests.delete","delete_vol_habilidad", 'http://localhost:5050/api/parallelDelete/vol_habilidad/all/',None)


###############################################################################
def get_data(cols, x):
    data={}
    for key, value in cols.items():
        v=None
        if value["type"]=="num":
            v = x
        elif value["type"]=="txt":
            v = value["value"].replace("X",str(x))
        elif value["type"]=="dep":
            v = random.randrange(value["value"][0],value["value"][1])
        data[key]=v
    return data

###############################################################################
def create_tabla(amount,data_template,action, url):
    for i in range(amount):
        data = get_data(data_template, i)
        run_http_func("requests.post",action, url,data)

###############################################################################
def create_tablas(tables):
    create_tabla(tables["voluntario"],
                 {"id":{"type":"num","value":0}, "nombre": {"type":"txt","value":"NOMBRE_X"}, "fnacimiento": {"type":"txt","value":"1999-05-12"}},
                 "insert_voluntario", 'http://localhost:5050/api/parallelInsert/voluntario/')

    create_tabla(tables["vol_habilidad"],
                 {"id":{"type":"num","value":0},
                  "id_voluntario": {"type":"num","value":0},
                  "id_habilidad": {"type":"num","value":0}
                  },
                 "insert_vol_habilidad", 'http://localhost:5050/api/parallelInsert/vol_habilidad/')

    create_tabla(tables["habilidad"],
                 {"id":{"type":"num","value":0},
                  "descrip": {"type":"txt","value":"HABILIDAD_X"},
                  },
                 "insert_habilidad", 'http://localhost:5050/api/parallelInsert/habilidad/')

###############################################################################
def select_voluntariosPorId():
    run_http_func("requests.get","select_voluntariosPorId",
                  'http://localhost:5050/api/parallelByPrimary/voluntariosPorId/',None)

def select_voluntariosPorHabilidad():
    run_http_func("requests.get","select_voluntariosPorHabilidad",
                  'http://localhost:5050/api/parallelByPrimary/voluntariosPorHabilidad/',None)


def check_time(function, params):
    iniTime = current_milli_time()
    if params==None:
        eval(function)()
    else:
        eval(function)(params)
    print( function+" => time ms:"+str((current_milli_time() - iniTime))  )
    raw_input("Press Enter to continue... \n")


def main():
    delete_tablas()
    create_tablas({"voluntario":10,"habilidad":20,"vol_habilidad":10})

main()

