package ru.mos.it.udrvs.config


import scopt.{OParser, OParserBuilder}


object ConfigBuilder {
  private val builder: OParserBuilder[Config] = OParser.builder[Config]
  val parser: OParser[Unit, Config] = {
    import builder._
    OParser.sequence(
      programName("java - jar getDataFromUlk.jar"),
      head("ApiRipper v.0.1"),
      opt[String]('d', "driver")
        .optional()
        .action((x,c)=>c.copy(driver = x))
        .text("optional now only com.vertica.jdbc.Driver is supported"),
      opt[String]('h', "host")
        .required()
        .action((x,c)=>c.copy(host = x))
        .text("mandatory ip-address where db is hosted"),
      opt[String]('p', "port")
        .required()
        .action((x,c)=>c.copy(port = x))
        .text("mandatory db port"),
      opt[String]('n', "dbName")
        .required()
        .action((x,c)=>c.copy(dbName = x))
        .text("mandatory database name"),
      opt[String]('u', "user")
        .required()
        .action((x,c)=>c.copy(user = x))
        .text("mandatory database user"),
      opt[String]("psw")
        .required()
        .action((x,c)=>c.copy(psw = x))
        .text("mandatory database user password"),
      opt[String]('s', "schema")
        .optional()
        .action((x,c)=>c.copy(schema = x))
        .text("optional schema name, public will be used by default"),
      opt[String]('t', "table")
        .required()
        .action((x,c)=>c.copy(table = x))
        .text("mandatory table name"),
      opt[String]('f', "field")
        .required()
        .action((x,c)=>c.copy(field = x))
        .text("mandatory field name in table to enrich"),
      opt[String]('r',"restApi")
        .required()
        .action((x,c)=>c.copy(restApi = x))
        .text("mandatory rest api uri"),
      opt[String]("system")
        .required()
        .action((x,c)=>c.copy(system = x))
        .text("mandatory system name"),
      opt[String]("token")
        .required()
        .action((x,c)=>c.copy(token = x))
        .text("mandatory system token"),
      opt[String]('a', "authBearer")
        .required()
        .action((x,c)=>c.copy(authBearer = x))
        .text("mandatory authBearer"),
      opt[String]("dictSchema")
        .required()
        .action((x,c)=>c.copy(dictSchema = x))
        .text("mandatory schema name where result as dictionary will be saved"),
      opt[String]("dictTable")
        .required()
        .action((x,c)=>c.copy(dictTable = x))
        .text("mandatory table name where result as dictionary will be saved. If table is not exist, it will be created"),
      help("help").text("print this usage text")
    )
  }

}
