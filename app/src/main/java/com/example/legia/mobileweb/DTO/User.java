package com.example.legia.mobileweb.DTO;

import java.io.Serializable;

public class User implements Serializable {
        private static final long serialVersionUID = 1L;
        private int iduser;
        private String username;
        private String password;
        private String ho_user;
        private String ten_user;
        private int sdt;
        private String email;
        private String dia_chi;
        private String thanh_pho;
        private String nuoc;
        private String zip_code;
        private String quan;
        private String phuong;
        private int id_the_tich_diem;
        private String loai_the;
        private int diem;

        public User() {
            super();
        }


        public int getIduser() {
            return iduser;
        }



        public void setIduser(int iduser) {
            this.iduser = iduser;
        }



        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getHo_user() {
            return ho_user;
        }

        public void setHo_user(String ho_user) {
            this.ho_user = ho_user;
        }

        public String getTen_user() {
            return ten_user;
        }

        public void setTen_user(String ten_user) {
            this.ten_user = ten_user;
        }

        public int getSdt() {
            return sdt;
        }

        public void setSdt(int sdt) {
            this.sdt = sdt;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDia_chi() {
            return dia_chi;
        }

        public void setDia_chi(String dia_chi) {
            this.dia_chi = dia_chi;
        }



        public String getQuan() {
            return quan;
        }


        public void setQuan(String quan) {
            this.quan = quan;
        }


        public String getPhuong() {
            return phuong;
        }


        public void setPhuong(String phuong) {
            this.phuong = phuong;
        }


        public String getThanh_pho() {
            return thanh_pho;
        }

        public void setThanh_pho(String thanh_pho) {
            this.thanh_pho = thanh_pho;
        }

        public String getNuoc() {
            return nuoc;
        }

        public void setNuoc(String nuoc) {
            this.nuoc = nuoc;
        }

        public String getZip_code() {
            return zip_code;
        }

        public void setZip_code(String zip_code) {
            this.zip_code = zip_code;
        }

    public int getId_the_tich_diem() {
        return id_the_tich_diem;
    }

    public void setId_the_tich_diem(int id_the_tich_diem) {
        this.id_the_tich_diem = id_the_tich_diem;
    }

    public String getLoai_the() {
        return loai_the;
    }

    public void setLoai_the(String loai_the) {
        this.loai_the = loai_the;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }
}
