package ru.mos.it.udrvs.dbutil

import cats.implicits._
import doobie.implicits._
import doobie.util.fragment
import doobie.{ConnectionIO, Fragment, Update}
import ru.mos.it.udrvs.config.Config
import ru.mos.it.udrvs.config.Data.{Legal, Message}




object Sql {



  //TODO you need to make join source tab and target tab on field param
  def makeSql(config: Config): fragment.Fragment = fr"select distinct" ++  Fragment.const(config.field) ++
    fr"from" ++ Fragment.const(config.schema) ++ fr"." ++ Fragment.const(config.table)

  //TODO this hardcode version, you have to rewrite it more intelligently
  def createDictSql(config: Config): Fragment = fr"create table if not exists" ++
    Fragment.const(config.dictSchema) ++ fr"." ++
    Fragment.const(config.dictTable) ++ fr" (" ++
    fr"ID int," ++
    fr"P_LEGAL_ID int," ++
    fr"CORP_ID varchar(128),"  ++
    fr"IS_IP varchar(4)," ++
    fr"FULL_NAME varchar(1024)," ++
    fr"PHONE varchar(64)," ++
    fr"EMAIL varchar(1024)," ++
    fr"IS_DELETED varchar(4)," ++
    fr"SHORT_NAME varchar(1024)," ++
    fr"CREATE_DATE varchar(128)," ++
    fr"UPDATED_DATE varchar(128)," ++
    fr"REG_DATE varchar(128)," ++
    fr"OGRN varchar(64)," ++
    fr"INN varchar(64)," ++
    fr"KPP varchar(64)," ++
    fr"OPF varchar(64)," ++
    fr"OKPO varchar(64)," ++
    fr"OKTMO varchar(64)," ++
    fr"HEAD_SURNAME varchar(1024)," ++
    fr"HEAD_NAME varchar(1024)," ++
    fr"HEAD_PATRONYMIC varchar(1024)," ++
    fr"HEAD_POSITION varchar(1024)," ++
    fr"LAW_STATUS_CODE varchar(1024)," ++
    fr"LAW_STATUS_NAME varchar(1024)," ++
    fr"EGRUL_UPD_DATE varchar(128)," ++
    fr"EGRUL_REQ_DATE varchar(128)," ++
    fr"IN_EGRULIP varchar(128)," ++
    fr"EGRUL_REQ_STATUS varchar(128))"

  //TODO this hardcode version, you have to rewrite it more intelligently
  def insertSql(config: Config): String = s"insert into ${config.dictSchema}.${config.dictTable}" +
    s" (ID," +
    s" P_LEGAL_ID," +
    s" CORP_ID," +
    s" IS_IP," +
    s" FULL_NAME," +
    s" PHONE," +
    s" EMAIL," +
    s" IS_DELETED," +
    s" SHORT_NAME," +
    s" CREATE_DATE," +
    s" UPDATED_DATE," +
    s" REG_DATE," +
    s" OGRN," +
    s" INN," +
    s" KPP," +
    s" OPF," +
    s" OKPO," +
    s" OKTMO," +
    s" HEAD_SURNAME," +
    s" HEAD_NAME," +
    s" HEAD_PATRONYMIC," +
    s" HEAD_POSITION," +
    s" LAW_STATUS_CODE," +
    s" LAW_STATUS_NAME," +
    s" EGRUL_UPD_DATE," +
    s" EGRUL_REQ_DATE," +
    s" IN_EGRULIP," +
    s" EGRUL_REQ_STATUS"+
    s") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

  def insertMany(msgs: List[Message], insertSql: String): ConnectionIO[Int] = {
    Update[Legal](insertSql).updateMany(msgs.map(_.data))
  }

}
