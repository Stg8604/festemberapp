package edu.nitt.deltaapp.core.model.payload.Hospitality

import com.google.gson.annotations.SerializedName
import edu.nitt.deltaapp.core.model.BaseResponse

class HospitalityDataResponse(
  @SerializedName("docs")
  val Hospi: List<HospitalityData>,

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

) : BaseResponse<List<HospitalityData>>()
