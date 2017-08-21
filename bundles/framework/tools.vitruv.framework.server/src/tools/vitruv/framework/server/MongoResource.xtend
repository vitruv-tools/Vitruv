package tools.vitruv.framework.server

import java.io.File
import java.io.FileReader
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

import org.apache.log4j.Logger

import org.bson.Document

import org.eclipse.xtend.lib.annotations.Accessors

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase

abstract class MongoResource {
	// Extensions. 
	protected static extension Gson = new GsonBuilder().create
	protected static extension JsonParser = new JsonParser
	static extension Logger = Logger::getLogger(MongoResource)

	// Static values.
	static val FILE_PATH = "/opt/config/vitruv_server.json"

	// Variables.
	@Accessors(PROTECTED_GETTER)
	MongoDatabase database

	@Accessors(PROTECTED_GETTER)
	MongoCollection<Document> collection

	protected MongoClient mongoClient

	protected abstract def String getCollectionName()

	private def String getDatabaseUrl() {
		val configFile = new File(FILE_PATH)
		if (configFile.exists && !configFile.directory) {
			debug('''configFile = «configFile.toString»''')
			val jsonElement = parse(new FileReader(configFile))
			if (null !== jsonElement && jsonElement.jsonObject) {
				val jsonObject = jsonElement.asJsonObject
				val dbpath = jsonObject?.get("databaseURL")?.asString
				if (null !== dbpath) {
					debug('''currentDatabaseURL = «dbpath»''')
					return dbpath
				}
			}
		}
		return "localhost:27017"
	}

	@PostConstruct
	def void postConstruct() {
		val currentDatabaseURL = databaseUrl
		mongoClient = new MongoClient(currentDatabaseURL)
		database = mongoClient.getDatabase("vitruv_versioning")
		collection = database.getCollection(collectionName)
	}

	@PreDestroy
	def void preDestroy() {
		mongoClient.close
	}

	protected def collectionExists(String collectionName) {
		return database.listCollectionNames.exists[it == collectionName]
	}
}
