package com.tbd.ejemplo1.repositories;

import com.tbd.ejemplo1.models.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.ArrayList;

@Repository
public class VoluntarioRepositoryImp implements VoluntarioRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Voluntario> getAllVol() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select * from voluntario").executeAndFetch(Voluntario.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public int countVoluntarios() {
        int total = 0;
        try(Connection conn = sql2o.open()){
            total = conn.createQuery("SELECT COUNT(*) FROM voluntario").executeScalar(Integer.class);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return total;
    }

    @Override
    public List<Voluntario> getVolForHab(String habilidad){
        habilidad = Character.toUpperCase(habilidad.charAt(0)) +  habilidad.substring(1);
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT DISTINCT v.id,v.nombre,v.fnacimiento FROM voluntario AS v, vol_habilidad AS vh, habilidad AS h WHERE v.id = vh.id_voluntario AND h.id = vh.id_habilidad AND h.descrip = '" + habilidad +"' order by v.id").executeAndFetch(Voluntario.class);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Voluntario> getVolForEme(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("select DISTINCT c.id,c.nombre,c.fnacimiento from tarea a, ranking b, voluntario c, emergencia e where a.id = b.id_tarea and b.id_voluntario = c.id and e.id = a.id_emergencia ").executeAndFetch(Voluntario.class);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    /* Para getVolForEme puede ser:
    
        select DISTINCT v.id,v.nombre,v.fnacimiento , e.nombre
        from tarea t, ranking r, voluntario v, emergencia e, estado_tarea et
        where t.id = r.id_tarea and r.id_voluntario = v.id and e.id = t.id_emergencia and et.id = t.id
            and et.descrip = 'Finalizado'
        UNION
        select DISTINCT v.id,v.nombre,v.fnacimiento , e.nombre
        from tarea t, ranking r, voluntario v, emergencia e, estado_tarea et
        where t.id = r.id_tarea and r.id_voluntario = v.id and e.id = t.id_emergencia and et.id = t.id
            and et.descrip = 'EnEjecucion'
    */

}
