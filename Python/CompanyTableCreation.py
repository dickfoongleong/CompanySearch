import mysql.connector

def connectDatabase():
	conn = mysql.connector.connect(host = "localhost", user = "dleong", password = "Dickfoong0314", database = "company_list")
	
	print('Database Connected')

	return conn

def createTable(cursor):
	cursor.execute("CREATE TABLE IF NOT EXISTS companies (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), website VARCHAR(255))")
	cursor.execute("SHOW TABLES")
	for x in cursor:
		print(x)

	cursor.execute("DESC companies")
	for x in cursor:
		print(x)
def main():
	dbConn = connectDatabase()
	dbCursor = dbConn.cursor()
	createTable(dbCursor)

	dbCursor.close()
	dbConn.close()
	
main()
