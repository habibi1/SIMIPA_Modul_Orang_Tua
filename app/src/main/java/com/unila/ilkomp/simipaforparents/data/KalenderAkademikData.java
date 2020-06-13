package com.unila.ilkomp.simipaforparents.data;

import com.unila.ilkomp.simipaforparents.model.KalenderAkademik;

import java.util.ArrayList;

public class KalenderAkademikData {

    private static String tahunAjaran = "2019/2020";

    private static String semester = "ganjil";

    private static String[] namaKegiatan = {
            "SBMPTN",
            "SNMPTN",
            "Kuliah",
            "Libur Akademik"
    };

    private static String[] tanggalKegiatan = {
            "12-13 Agustus 2019",
            "13-18 Agustus 2019",
            "5 September - 1 Oktober 2019",
            "2 Oktober 2019"
    };

    public static ArrayList<KalenderAkademik> getListData() {
        ArrayList<KalenderAkademik> list = new ArrayList<>();
        for (int position = 0; position < namaKegiatan.length; position++) {
            KalenderAkademik kalenderAkademik = new KalenderAkademik();
            kalenderAkademik.setTahunAjaran(tahunAjaran);
            kalenderAkademik.setSemester(semester);
            kalenderAkademik.setNamaKegiatan(namaKegiatan[position]);
            kalenderAkademik.setTanggal(tanggalKegiatan[position]);
            list.add(kalenderAkademik);
        }
        return list;
    }

}
