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
import com.edusys.entity.hocVien;

/**
 *
 * @author Sieu Nhan Bay
 */
public class hocVienDAO {
    
    //đọc 1 nhân viên từ 1 bản ghi (1 ResultSet)
    public hocVien readFromResultSet(ResultSet rs) throws SQLException{
        hocVien model=new hocVien();
        model.setMaHV(rs.getInt("MaHV"));
         model.setMaKH(rs.getInt("KH"));
         model.setMaNH(rs.getString("MaNH"));
         model.setDiem(rs.getDouble("Diem"));
         return model;
    }
    
    //thực hiện truy vấn lấy về 1 tập ResultSet rồi điền tập ResultSet đó vào 1 List
    public List<hocVien> select(String sql,Object...args){
        List<hocVien> list=new ArrayList<>();
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
    

    public void insert(hocVien model) {
        String sql="INSERT INTO HocVien(MaKH, MaNH, Diem) VALUES(?, ?, ?)";
        jdbcHelper.executeUpdate(sql,
                model.getMaKH(),
                 model.getMaNH(),
                 model.getDiem());
    }


    public void update(hocVien model) {
        String sql="UPDATE HocVien SET MaKH=?, MaNH=?, Diem=? WHERE MaHV=?";
        jdbcHelper.executeUpdate(sql,
                model.getMaKH(),
                 model.getMaNH(),
                 model.getDiem(),
                 model.getMaHV());
    }


     public void delete(Integer MaHV){
     String sql="DELETE FROM HocVien WHERE MaHV=?";
     jdbcHelper.executeUpdate(sql, MaHV);
     }



    public List<hocVien> select() {
        String sql="SELECT * FROM HocVien";
        return select(sql);             //trong 1 class có thể có 2 method trùng tên (nhưng param khác nhau)
    }


    public hocVien findById(String id) {
        String sql="SELECT * FROM HocVien WHERE MaHV=?";
        List<hocVien> list=select(sql, id);
        return list.size()>0?list.get(0):null;               //có thể trả về là null
    }   
     public List<hocVien> selectByKhoaHoc(int maKH) {
        String sql="SELECT * FROM HocVien WHERE MaKH=?";
        return this.selectBySql(sql, maKH);
    }
     public hocVien selectById(Integer mahv){
        String sql="SELECT * FROM HocVien WHERE MaHV=?";
        List<hocVien> list = select(sql, mahv);
        return list.size() > 0 ? list.get(0) : null;
    }
    protected List<hocVien> selectBySql(String sql, Object...args){
        List<hocVien> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = jdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    hocVien entity=new hocVien();
                    entity.setMaHV(rs.getInt("MaHV"));
                    entity.setMaKH(rs.getInt("MaKH"));
                    entity.setMaNH(rs.getString("MaNH"));
                    entity.setDiem(rs.getDouble("Diem"));
                    list.add(entity);
                }
            } 
            finally{
                rs.getStatement().getConnection().close();
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
      public List<hocVien> selectAll(){
        String sql="SELECT * FROM HocVien";
        return selectBySql(sql);
    }
}
