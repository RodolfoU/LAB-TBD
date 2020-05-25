package com.tbd.ejemplo1.repositories;

import com.tbd.ejemplo1.models.Institucion;
import com.tbd.ejemplo1.models.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class TareaRepositoryImp implements TareaRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Tarea> getAllTar() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from tarea").executeAndFetch(Tarea.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Tarea> getTarForEmer(String emergencia) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select tar.id,tar.nombre, tar.descrip,tar.cant_vol_requeridos,tar.cant_vol_inscritos,tar.finicio,tar.ffin,tar.id_estado from tarea tar, emergencia emer where tar.id_emergencia = emer.id and emer.nombre = '"+emergencia+"'").executeAndFetch(Tarea.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
