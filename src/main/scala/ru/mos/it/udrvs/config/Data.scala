package ru.mos.it.udrvs.config

import io.circe.generic.extras.{Configuration, ConfiguredJsonCodec, JsonKey}


object Data {


  case class Message(error: Int, data: Legal)
  object Message {
    import ru.mos.it.udrvs.config.Data.Legal.EmptyLegal
    val EmptyMessage: Message = Message(1, EmptyLegal)
  }


  @ConfiguredJsonCodec case class Legal(@JsonKey("ID") id: Int,
                                        @JsonKey("P_LEGAL_ID") pLegalId: Int,
                                        @JsonKey("CORP_ID") corpId: Option[String],
                                        @JsonKey("IS_IP") isIp: Option[String],
                                        @JsonKey("FULL_NAME") fullName: Option[String],
                                        @JsonKey("PHONE") phone: Option[String],
                                        @JsonKey("EMAIL") email: Option[String],
                                        @JsonKey("IS_DELETED") isDeleted: Option[String],
                                        @JsonKey("SHORT_NAME") shortName: Option[String],
                                        @JsonKey("CREATE_DATE") createDate: Option[String],
                                        @JsonKey("UPDATED_DATE") ipdatedDate: Option[String],
                                        @JsonKey("REG_DATE") regDate: Option[String],
                                        @JsonKey("OGRN") ogrn: Option[String],
                                        @JsonKey("INN") inn: Option[String],
                                        @JsonKey("KPP") kpp: Option[String],
                                        @JsonKey("OPF") opf: Option[String],
                                        @JsonKey("OKPO") okpo: Option[String],
                                        @JsonKey("OKTMO") oktmo: Option[String],
                                        @JsonKey("HEAD_SURNAME") headSurname: Option[String],
                                        @JsonKey("HEAD_NAME") headNAme: Option[String],
                                        @JsonKey("HEAD_PATRONYMIC") headPatronomyc: Option[String],
                                        @JsonKey("HEAD_POSITION") headPosition: Option[String],
                                        @JsonKey("LAW_STATUS_CODE") lawStatusCode: Option[String],
                                        @JsonKey("LAW_STATUS_NAME") lawStatusName: Option[String],
                                        @JsonKey("EGRUL_UPD_DATE") egrulUpdDate: Option[String],
                                        @JsonKey("EGRUL_REQ_DATE") egrulReqDate:Option[String],
                                        @JsonKey("IN_EGRULIP") inEgrulIp: Option[String],
                                        @JsonKey("EGRUL_REQ_STATUS") egrulReqStatus: Option[String])

  object Legal {
    implicit val config: Configuration = Configuration.default
    val EmptyLegal: Legal = Legal(0, 0,
      Some("ошибка получения"),Some("ошибка получения"),Some("ошибка получения"),Some("ошибка получения"),
      Some("ошибка получения"),Some("ошибка получения"),Some("ошибка получения"),Some("ошибка получения"),
      Some("ошибка получения"),Some("ошибка получения"),Some("ошибка получения"),Some("ошибка получения"),
      Some("ошибка получения"),Some("ошибка получения"),Some("ошибка получения"),Some("ошибка получения"),
      Some("ошибка получения"),Some("ошибка получения"),Some("ошибка получения"),Some("ошибка получения"),
      Some("ошибка получения"),Some("ошибка получения"),Some("ошибка получения"),Some("ошибка получения"),
      Some("ошибка получения"),Some("ошибка получения")
    )
  }

}
