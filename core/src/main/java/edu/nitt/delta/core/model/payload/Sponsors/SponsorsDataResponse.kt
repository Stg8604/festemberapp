package edu.nitt.delta.core.model.payload.Sponsors

import com.google.gson.annotations.SerializedName
import edu.nitt.delta.core.model.BaseResponse

class SponsorsDataResponse(
  @SerializedName("docs")
  val entry: List<SponsorsData>,

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

) : BaseResponse<List<SponsorsData>>()
