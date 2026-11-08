package com.example.practica5cr.datadao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InterAlumnoDao {
    @Query("SELECT * FROM alumnos ORDER BY matricula ASC")
    suspend fun getAlumnos(): List<Alumno>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarOActualizar(alumno: Alumno)

    @Query("DELETE FROM alumnos WHERE matricula = :matricula")
    suspend fun eliminarPorMatricula(matricula: String): Int

    @Query("SELECT * FROM alumnos WHERE matricula = :matricula LIMIT 1")
    suspend fun buscarPorMatricula(matricula: String): Alumno?
}
