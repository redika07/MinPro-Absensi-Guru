package id.ac.polinema.absenguruprivate.rest;

import java.util.List;
import java.util.Map;

import id.ac.polinema.absenguruprivate.model.AbsenItem;
import id.ac.polinema.absenguruprivate.model.GuruItem;
import id.ac.polinema.absenguruprivate.model.SiswaItem;
import id.ac.polinema.absenguruprivate.model.User;
import id.ac.polinema.absenguruprivate.model.AbsenItem;
import id.ac.polinema.absenguruprivate.model.GuruItem;
import id.ac.polinema.absenguruprivate.model.SiswaItem;
import id.ac.polinema.absenguruprivate.model.User;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface ApiInterface {
    @POST("loginAdmin")
    Call<ResponseBody> loginAdmin(@Body User user);

    @POST("loginGuru")
    Call<ResponseBody> loginGuru(@Body User user);

    @GET("dataGuru")
    Call<List<GuruItem>> getGuru();

    @GET("dataGuru/{username}")
    Call<List<GuruItem>> getGuruByUsername(
            @Path("username") String username
    );

    @GET("dataSiswa")
    Call<List<SiswaItem>> getSiswa();

    @POST("dataSiswa")
    Call<ResponseBody> tambahSiswa(@Body SiswaItem siswa);

    @GET("absenGuru/{username}")
    Call<List<AbsenItem>> getAbsenByUsername(
            @Path("username") String username
    );

    @POST("absenGuru")
    Call<ResponseBody> absenGuru(@Body AbsenItem absen);

    @Multipart
    @POST("dataGuru")
    Call<ResponseBody> tambahGuru(
            @Part MultipartBody.Part photo,
            @PartMap Map<String, RequestBody> text);

}
