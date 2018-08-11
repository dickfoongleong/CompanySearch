import mysql.connector

def databaseConnection():
	conn = mysql.connector.connect(host = "localhost", user = "dleong", password = "Dickfoong0314", database = "company_list")
	print("Database connected")
	return conn

def loadData():
	inputFile = open("../csv/companylist.csv", "r")
	print("Data is loaded")
	return inputFile.readlines()

def insertData(cursor, list):
	for l in list:
		sql = "INSERT INTO companies (name) VALUES (%s)"%l
		cursor.execute(sql)

def main():
	dbConn = databaseConnection()
	dbCursor = dbConn.cursor()
	dbCursor.execute("TRUNCATE TABLE companies")
	companyList = loadData()
	insertData(dbCursor, companyList)
	dbConn.commit()
	dbCursor.execute("SELECT COUNT(*) FROM companies")
	print("%d Records Added."%dbCursor.fetchone())
	
	dbCursor.close()
	dbConn.close()

main()
