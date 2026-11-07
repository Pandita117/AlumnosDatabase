package com.example.practica5cr.`data-dao`


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alumnos")
data class Alumno(
        @PrimaryKey val matricula: String,
        val nombre: String,
        var edad: Int,
        var Grupo: Int,
        var Seccion: Char
    ) {
        companion object {
            fun matriculaIsValid(matricula: String): Boolean {
                return matricula.length <= 10 && matricula.all { it.isDigit() }
            }

            fun edadIsValid(edadStr: String): Boolean {
                if (edadStr.contains("e", ignoreCase = true)) return false
                val edad_numerica = edadStr.toIntOrNull() ?: return false
                return  edad_numerica > 0
            }

            fun matriculaExiste(matricula: String, lista : List<Alumno>): Boolean{
                return lista.any { it.matricula == matricula }

            }

        }
    }

