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
import com.edusys.entity.nhanVien;

/**
 *
 * @author Sieu Nhan Bay
 */
public class nhanVienDAO {
    
    //đọc 1 nhân viên từ 1 bản ghi (1 ResultSet)
    public nhanVien readFromResultSet(ResultSet rs) throws SQLException{
        nhanVien model=new nhanVien();
        model.setMaNV(rs.getString("MaNV"));
        model.setMatKhau(rs.getString("MatKhau"));
        model.setHoTen(rs.getString("HoTen"));
        model.setVaiTro(rs.getBoolean("VaiTro"));
        return model;
    }
    
    //thực hiện truy vấn lấy về 1 tập ResultSet rồi điền tập ResultSet đó vào 1 List
    public List<nhanVien> select(String sql,Object...args){
        List<nhanVien> list=new ArrayList<>();
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
     * @param entity là thực thể chứa thông tin bản ghi mới
     */
    public void insert(nhanVien entity) {
        String sql="INSERT INTO NhanVien (MaNV, MatKhau, HoTen, VaiTro) VALUES (?, ?, ?, ?)";
        jdbcHelper.executeUpdate(sql,
                entity.getMaNV(),
                entity.getMatKhau(),
                entity.getHoTen(),
                entity.isVaiTro());
    }

    /**
     * Cập nhật thực thể vào CSDL
     * @param entity là thực thể chứa thông tin bản ghi cần cập nhật
     */
    public void update(nhanVien entity) {
        String sql="UPDATE NhanVien SET MatKhau=?, HoTen=?, VaiTro=? WHERE MaNV=?";
        jdbcHelper.executeUpdate(sql,
                entity.getMatKhau(),
                entity.getHoTen(),
                entity.isVaiTro(),
                entity.getMaNV());
    }

    /**
     * Xóa bản ghi khỏi CSDL
     * @param maNV là mã của bản ghi cần xóa
     */
    public void delete(String maNV) {
        String sql="DELETE FROM NhanVien WHERE MaNV=?";
        jdbcHelper.executeUpdate(sql, maNV);
    }

    /**
     * Truy vấn tất cả các các thực thể
     * @return danh sách các thực thể
     */
    public List<nhanVien> select() {
        String sql="SELECT * FROM NhanVien";
        return select(sql);             //trong 1 class có thể có 2 method trùng tên (nhưng param khác nhau)
    }

    /**
     * Truy vấn thực thể theo mã id
     * @param id là mã của bản ghi được truy vấn
     * @return thực thể chứa thông tin của bản ghi
     */
    public nhanVien findById(String id) {
        String sql="SELECT * FROM NhanVien WHERE MaNV=?";
        List<nhanVien> list=select(sql, id);
        return list.size()>0?list.get(0):null;               //có thể trả về là null
    }
}
