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
import com.edusys.entity.nguoiHoc;

/**
 *
 * @author Sieu Nhan Bay
 */
public class nguoiHocDAO {
    private nguoiHoc readFromResultSet(ResultSet rs) throws SQLException{
	nguoiHoc entity=new nguoiHoc();
         entity.setMaNH(rs.getString("MaNH"));
         entity.setHoTen(rs.getString("HoTen"));
         entity.setNgaySinh(rs.getDate("NgaySinh"));
         entity.setGioiTinh(rs.getBoolean("GioiTinh"));
         entity.setDienThoai(rs.getString("DienThoai"));
         entity.setEmail(rs.getString("Email"));
         entity.setGhiChu(rs.getString("GhiChu"));
         entity.setMaNV(rs.getString("MaNV"));
         entity.setNgayDK(rs.getDate("NgayDK"));
         return entity;
    }

    private List<nguoiHoc> select(String sql, Object...args){
        List<nguoiHoc> list=new ArrayList<>();
        try {
            ResultSet rs=null;
            try{
                rs=jdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    list.add(readFromResultSet(rs));
                }
            }finally{
                rs.getStatement().getConnection().close();
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
    public void insert(nguoiHoc entity) {
        String sql="INSERT INTO NguoiHoc (MaNH, HoTen, NgaySinh, GioiTinh, DienThoai, Email, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
         jdbcHelper.executeUpdate(sql,
             entity.getMaNH(),
             entity.getHoTen(),
             entity.getNgaySinh(),
             entity.isGioiTinh(),
             entity.getDienThoai(),
             entity.getEmail(),
             entity.getGhiChu(),
             entity.getMaNV());
    }

    /**
     * Cập nhật thực thể vào CSDL
     * @param model là thực thể chứa thông tin bản ghi cần cập nhật
     */
    public void update(nguoiHoc model) {
        String sql="UPDATE NguoiHoc SET HoTen=?, NgaySinh=?, GioiTinh=?, DienThoai=?, Email=?, GhiChu=?,MaNV=? WHERE MaNH=?";
        jdbcHelper.executeUpdate(sql,            
                 model.getHoTen(),
                 model.getNgaySinh(),
                 model.isGioiTinh(),
                 model.getDienThoai(),
                 model.getEmail(),
                 model.getGhiChu(),
                 model.getMaNV(),
                 model.getMaNH());
    }

    /**
     * Xóa bản ghi khỏi CSDL
     * @param id là mã của bản ghi cần xóa
     */
    public void delete(String id) {
        String sql="DELETE FROM NguoiHoc WHERE MaNH=?";
        jdbcHelper.executeUpdate(sql, id);
    }

    /**
     * Truy vấn tất cả các các thực thể
     * @return list danh sách các thực thể
     */
    public List<nguoiHoc> select() {
        String sql="SELECT * FROM NguoiHoc";
        return select(sql);
    }

    /**
     * Truy vấn người học theo keyword
     */
    public List<nguoiHoc> selectByKeyword(String keyword) {
        String sql="SELECT * FROM NguoiHoc WHERE HoTen LIKE ?";
        return select(sql, "%"+keyword+"%");
    }
    
    //truy xuất tất cả học viên không học khóa học maKH
     public List<nguoiHoc> selectByCourse(Integer makh){   //để là Integer cho đúng kiểu Object
     String sql="SELECT * FROM NguoiHoc WHERE MaNH NOT IN (SELECT MaNH FROM HocVien WHERE MaKH=?)";
     return select(sql, makh);
     }
     
     //truy xuất người học theo maNH
     public nguoiHoc findById(String manh){
     String sql="SELECT * FROM NguoiHoc WHERE MaNH=?";
     List<nguoiHoc> list = select(sql, manh);
     return list.size() > 0 ? list.get(0) : null;
     }
      public List<nguoiHoc> selectNotInCourse(int makh, String keyword) {
        String sql="SELECT * FROM NguoiHoc "
                + " WHERE HoTen LIKE ? AND "
                + " MaNH NOT IN (SELECT MaNH FROM HocVien WHERE MaKH=?)";
        return this.select(sql, "%"+keyword+"%", makh);
    }
     
}
