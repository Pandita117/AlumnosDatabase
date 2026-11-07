package com.example.practica5cr.`data-dao`

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InterAlumnoDao {
    //query para ver a los chavales
    @Query("SELECT * FROM alumnos ORDER BY matricula ASC")
    suspend fun getAlumnos(): List<Alumno>


    //aqui se inserta o se actualiza dependeidend0 de los uqe se quiera hacer
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarOActualizar(alumno: Alumno)

    //deleteo
    @Query("DELETE FROM alumnos WHERE matricula = :matricula")
    suspend fun eliminarPorMatricula(matricula : String): Int

    //buscar por matri
    @Query("SELECT * FROM alumnos WHERE matricula = :matricula LIMIT 1")
    suspend fun buscarPorMatricula(matricula: String): Alumno?



}