package com.unila.ilkomp.simipaforparents.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleRecord {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("kodeMK")
    @Expose
    private String kodeMK;
    @SerializedName("Mata_Kuliah")
    @Expose
    private String mataKuliah;
    @SerializedName("Prodi")
    @Expose
    private String prodi;
    @SerializedName("Ruang")
    @Expose
    private String ruang;
    @SerializedName("Nip1")
    @Expose
    private String nip1;
    @SerializedName("Dosen_PJ")
    @Expose
    private String dosenPJ;
    @SerializedName("Nip2")
    @Expose
    private Object nip2;
    @SerializedName("Dosen_Anggota")
    @Expose
    private Object dosenAnggota;
    @SerializedName("Hari")
    @Expose
    private String hari;
    @SerializedName("Tahun_Akademik")
    @Expose
    private String tahunAkademik;
    @SerializedName("Jenis")
    @Expose
    private String jenis;
    @SerializedName("Semester")
    @Expose
    private String semester;
    @SerializedName("Kelas")
    @Expose
    private String kelas;
    @SerializedName("Mulai")
    @Expose
    private String mulai;
    @SerializedName("Selesai")
    @Expose
    private String selesai;

    public ScheduleRecord(String mataKuliah, String ruang, String dosenPJ, String mulai, String selesai, String jenis) {
        this.mataKuliah = mataKuliah;
        this.ruang = ruang;
        this.dosenPJ = dosenPJ;
        this.mulai = mulai;
        this.selesai = selesai;
        this.jenis = jenis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKodeMK() {
        return kodeMK;
    }

    public void setKodeMK(String kodeMK) {
        this.kodeMK = kodeMK;
    }

    public String getMataKuliah() {
        return mataKuliah;
    }

    public void setMataKuliah(String mataKuliah) {
        this.mataKuliah = mataKuliah;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getRuang() {
        return ruang;
    }

    public void setRuang(String ruang) {
        this.ruang = ruang;
    }

    public String getNip1() {
        return nip1;
    }

    public void setNip1(String nip1) {
        this.nip1 = nip1;
    }

    public String getDosenPJ() {
        return dosenPJ;
    }

    public void setDosenPJ(String dosenPJ) {
        this.dosenPJ = dosenPJ;
    }

    public Object getNip2() {
        return nip2;
    }

    public void setNip2(Object nip2) {
        this.nip2 = nip2;
    }

    public Object getDosenAnggota() {
        return dosenAnggota;
    }

    public void setDosenAnggota(Object dosenAnggota) {
        this.dosenAnggota = dosenAnggota;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getTahunAkademik() {
        return tahunAkademik;
    }

    public void setTahunAkademik(String tahunAkademik) {
        this.tahunAkademik = tahunAkademik;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getMulai() {
        return mulai;
    }

    public void setMulai(String mulai) {
        this.mulai = mulai;
    }

    public String getSelesai() {
        return selesai;
    }

    public void setSelesai(String selesai) {
        this.selesai = selesai;
    }

}
