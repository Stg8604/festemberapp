package edu.nitt.deltaapp.core.model.payload.GuestLectures

import com.google.gson.annotations.SerializedName
import edu.nitt.deltaapp.core.model.BaseResponse

class GuestDataResponse(
  @SerializedName("docs")
  val guests: List<GuestData>,

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

) : BaseResponse<List<GuestData>>()
