import psycopg2
import time
import datetime


###############################################################################
def get_connection_db(conn):
    return psycopg2.connect(user=conn["user"],
                            password=conn["pass"],
                            host=conn["host"],
                            port=conn["port"],
                            database=conn["dbname"])



###############################################################################
def create_simple_insert_instruction(obj):

    columnName = ""
    columnValues = ""
    for col in obj["columns"]:
        columnName =  columnName + col + ","
        columnValues =  columnValues + "%s,"

    query = "INSERT INTO "+obj["tableName"]+" ("+columnName[:(len(columnName)-1)]+") VALUES ("+columnValues[:(len(columnValues)-1)]+")"
    return query

###############################################################################
def insert_table(table,connData):
    resp="ok"
    try:
        connection = get_connection_db(connData)
        cursor = connection.cursor()

        cursor.execute(table["query"], table["data"])

        connection.commit()
        print ("registros en la tabla "+ table["tableName"] )


    except (Exception, psycopg2.Error) as error :
        resp=str(error)
        if(connection):
            print("Failed to insert record into "+table["tableName"]+" table", error)

    finally:
        #closing database connection.
        if(connection):
            cursor.close()
            connection.close()
            print("PostgreSQL connection is closed")

    return resp

###############################################################################
def select_data(sqlStatement,connData):
    resp=[]
    try:
        connection = get_connection_db(connData)
        cursor = connection.cursor()

        cursor.execute(sqlStatement)
        colnames = [desc[0] for desc in cursor.description]
        lenColnames = len(colnames)

        print("Selecting rows from mobile table using cursor.fetchall")
        records = cursor.fetchall()

        print("Print each row and it's columns values")
        for row in records:
            obj={}
            for i in range(lenColnames):
                obj[ colnames[i] ] = str(row[i])
            resp.append(obj)

    except (Exception, psycopg2.Error) as error :
        if(connection):
            print("Failed > ", error)

    finally:
        if(connection):
            cursor.close()
            connection.close()
            print("PostgreSQL connection is closed")

    return resp

###############################################################################
def delete_table(tableName,connData):

    resp="ok"

    try:
        connection = get_connection_db(connData)
        cursor = connection.cursor()
        #borrar todo para la prueba
        cursor.execute("delete from "+tableName)
        connection.commit()

        print ("se eliminaron los datos de la tabla "+ tableName )

    except (Exception, psycopg2.Error) as error :
        resp=str(error)
        if(connection):
            print("Failed delete => "+tableName+" table", error)

    finally:
        #closing database connection.
        if(connection):
            cursor.close()
            connection.close()
            print("PostgreSQL connection is closed")

    return resp

##############################################################################
def get_info_insert(jdata):
    columns=[]
    values=[]
    for key,value in jdata.iteritems():
        columns.append(key)
        values.append(value)

    return columns, values


##############################################################################
def insert_row(dbConnConfig,tableName, jdata):

    columns, values = get_info_insert(jdata)
    table={"tableName":tableName, "columns": columns,"data": values }
    table["query"]=create_simple_insert_instruction(table)
    resp = insert_table(table,dbConnConfig)
    return resp
