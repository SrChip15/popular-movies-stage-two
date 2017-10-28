package com.example.android.flixt.service.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
@Entity(tableName = "movies")
public class Movie implements Parcelable {
	public static final Creator<Movie> CREATOR = new Creator<Movie>() {
		@Override
		public Movie createFromParcel(Parcel in) {
			return new Movie(in);
		}

		@Override
		public Movie[] newArray(int size) {
			return new Movie[size];
		}
	};
	private static final int FAVORITE = 1;
	private static final int NOT_FAVORITE = 0;
	@SerializedName("vote_count")
	@ColumnInfo(name = "vote_count")
	private Integer voteCount;
	@SerializedName("id")
	@PrimaryKey
	@ColumnInfo(name = "movie_id")
	private int id;
	@SerializedName("video")
	@Ignore
	@SuppressWarnings("unused")
	private Boolean video;
	@SerializedName("vote_average")
	@ColumnInfo(name = "vote_average")
	private Double voteAverage;
	@SerializedName("title")
	private String title;
	@SerializedName("popularity")
	private Double popularity;
	@ColumnInfo(name = "poster_path")
	@SerializedName("poster_path")
	private String posterPath;
	@SerializedName("original_title")
	@Ignore
	private String originalTitle;
	@ColumnInfo(name = "backdrop_path")
	@SerializedName("backdrop_path")
	private String backdropPath;
	@SerializedName("overview")
	private String overview;
	@ColumnInfo(name = "release_date")
	@SerializedName("release_date")
	private String releaseDate;
	@ColumnInfo(name = "is_movie_favorite")
	private int userPref = NOT_FAVORITE;

	@Ignore
	public Movie(Parcel in) {
		this.title = in.readString();
		this.posterPath = in.readString();
		this.originalTitle = in.readString();
		this.backdropPath = in.readString();
		this.overview = in.readString();
		this.releaseDate = in.readString();
		this.id = in.readInt();
		this.voteAverage = in.readDouble();
		this.voteCount = in.readInt();
		this.popularity = in.readDouble();
		this.userPref = in.readInt();
	}

	public Movie() {
		// Empty constructor check
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(title);
		parcel.writeString(posterPath);
		parcel.writeString(originalTitle);
		parcel.writeString(backdropPath);
		parcel.writeString(overview);
		parcel.writeString(releaseDate);
		parcel.writeInt(id);
		parcel.writeDouble(voteAverage);
		parcel.writeInt(voteCount);
		parcel.writeDouble(popularity);
		parcel.writeInt(userPref);
	}

	public Integer getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}

	public int getId() {
		return id;
	}

	void setId(int id) {
		this.id = id;
	}

	public int getUserPref() {
		return userPref;
	}

	public void setUserPref(int userPref) {
		this.userPref = userPref;
	}

	public Boolean getVideo() {
		return video;
	}

	public void setVideo(Boolean video) {
		this.video = video;
	}

	public Double getVoteAverage() {
		return voteAverage;
	}

	public void setVoteAverage(Double voteAverage) {
		this.voteAverage = voteAverage;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPopularity() {
		return popularity;
	}

	public void setPopularity(Double popularity) {
		this.popularity = popularity;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
}

