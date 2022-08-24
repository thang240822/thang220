/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.ultils.jdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.edusys.entity.khoaHoc;

/**
 *
 * @author Sieu Nhan Bay
 */
public class khoaHocDAO {
    
    //đọc 1 nhân viên từ 1 bản ghi (1 ResultSet)
    public khoaHoc readFromResultSet(ResultSet rs) throws SQLException{
        khoaHoc model=new khoaHoc();
         model.setMaKH(rs.getInt("MaKH"));
         model.setMaCD(rs.getString("MaCD"));
         model.setHocPhi(rs.getDouble("HocPhi"));
         model.setThoiLuong(rs.getInt("ThoiLuong"));
         model.setNgayKG(rs.getDate("NgayKG"));
         model.setGhiChu(rs.getString("GhiChu"));
         model.setMaNV(rs.getString("MaNV"));
         model.setNgayTao(rs.getDate("NgayTao"));        
         return model;
    }
    
    //thực hiện truy vấn lấy về 1 tập ResultSet rồi điền tập ResultSet đó vào 1 List
    public List<khoaHoc> select(String sql,Object...args){
        List<khoaHoc> list=new ArrayList<>();
        try {
            ResultSet rs=null;
            try{
                rs=jdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    list.add(readFromResultSet(rs));
                }
            }finally{
                rs.getStatement().getConnection().close();      //đóng kết nối từ resultSet
            }
        } catch (SQLException ex) {
            throw new RuntimeException();
        }
        return list;
    }
    
    /**
     * Thêm mới thực thể vào CSDL
     * @param model
     */
    public void insert(khoaHoc model) {
        String sql="INSERT INTO KhoaHoc (MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcHelper.executeUpdate(sql,
                model.getMaCD(),
                 model.getHocPhi(),
                 model.getThoiLuong(),
                 model.getNgayKG(),
                 model.getGhiChu(),
                 model.getMaNV());
    }

    /**
     * Cập nhật thực thể vào CSDL
     * @param model
     */
    public void update(khoaHoc model) {
        String sql="UPDATE KhoaHoc SET MaCD=?, HocPhi=?, ThoiLuong=?, NgayKG=?, GhiChu=?, MaNV=? WHERE MaKH=?";
        jdbcHelper.executeUpdate(sql,
                model.getMaCD(),
                 model.getHocPhi(),
                 model.getThoiLuong(),
                 model.getNgayKG(),
                 model.getGhiChu(),
                 model.getMaNV(),
                 model.getMaKH());
    }

    /**
     * Xóa bản ghi khỏi CSDL
     * @param MaKH
     */
     public void delete(Integer MaKH){
     String sql="DELETE FROM KhoaHoc WHERE MaKH=?";
     jdbcHelper.executeUpdate(sql, MaKH);
     }

    /**
     * Truy vấn tất cả các các thực thể
     * @return danh sách các thực thể
     */
    public List<khoaHoc> select() {
        String sql="SELECT * FROM KhoaHoc";
        return select(sql);             //trong 1 class có thể có 2 method trùng tên (nhưng param khác nhau)
    }

     public khoaHoc findById(Integer makh){
     String sql="SELECT * FROM KhoaHoc WHERE MaKH=?";
     List<khoaHoc> list = select(sql, makh);
     return list.size() > 0 ? list.get(0) : null;
     }    
      public List<khoaHoc> selectByChuyenDe(String macd){
        String sql="SELECT * FROM KhoaHoc WHERE MaCD=?";
        return this.select(sql, macd);
    }
}
