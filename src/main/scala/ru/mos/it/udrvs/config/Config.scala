package ru.mos.it.udrvs.config



case class Config(driver: String = "com.vertica.jdbc.Driver",
                  host: String = "",
                  port: String = "",
                  dbName: String ="",
                  user: String ="",
                  psw: String ="",
                  schema: String = "public",
                  table: String ="",
                  field: String ="",
                  restApi: String ="",
                  system: String ="",
                  token: String ="",
                  authBearer: String ="",
                  dictSchema: String ="",
                  dictTable: String ="")
