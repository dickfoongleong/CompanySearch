import mysql.connector

def databaseConnet():
	try:
		conn = mysql.connector.connect(host="localhost", user="dleong", password="Dickfoong0314", database="company_list")
		print("Connected to Database")
		
		return conn
	except ERROR as error:
		print(error)

def main():

main()
