package edu.nitt.deltaapp.core.model.payload.Informals

import com.google.gson.annotations.SerializedName
import edu.nitt.deltaapp.core.model.BaseResponse

class InformalsDataResponse(
  @SerializedName("docs")
  val entry: List<InformalsData>,

  @SerializedName("totalDocs")
  val totalDocs: Int,

  @SerializedName("limit")
  val limit: Int,

  @SerializedName("totalPages")
  val totalPages: Int,

  @SerializedName("page")
  val page: Int,

  @SerializedName("pagingCounter")
  val pagingCounter: Int,

  @SerializedName("hasPrevPage")
  val hasPrevPage: Boolean,

  @SerializedName("hasNextPage")
  val hasNextPage: Boolean,

  @SerializedName("prevPage")
  val prevPage: Int?,

  @SerializedName("nextPage")
  val nextPage: Int?

) : BaseResponse<List<InformalsData>>()
