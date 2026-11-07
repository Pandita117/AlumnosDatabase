package com.example.practica5cr
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.practica5cr.ui.theme.Practica5CRTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.RectangleShape
import com.example.practica5cr.`data-dao`.Alumno


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica5CRTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    registro_alumno()
                }
            }
        }
    }
}





@Composable
fun registro_alumno() {
    var elliminacion_alumno by remember { mutableStateOf<Alumno.alumno?>(null) }
    var eliminacion_matricula by remember { mutableStateOf("") }
    val lista_alumnos = remember { mutableStateListOf<Alumno.alumno>() }
    var alumno_editando by remember { mutableStateOf<Alumno.alumno?>(null) }
    var alumno_guardado by remember { mutableStateOf("") }
    var mensaje_para_nubs by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var grupo by remember { mutableStateOf("") }
    var seccion by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)

    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Matrícula
            OutlinedTextField(
                value = matricula,
                onValueChange = {
                    if (Alumno.alumno.matriculaIsValid(it)) {
                        matricula = it
                    }
                },
                label = { Text("Matrícula") },
                readOnly = alumno_editando != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Nombre (Permite letras y espacios)
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    if (it.all { char -> char.isLetter() || char.isWhitespace()  }) {
                        nombre= it.trim()
                    }
                },
                label = { Text("Nombre") }
            )

            // Edad
            OutlinedTextField(
                value = edad,
                onValueChange = {
                    if (Alumno.alumno.edadIsValid(it) || it.isEmpty()) {
                        edad = it
                    }
                },
                label = { Text("Edad") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Grupo y Sección
            LazyRow (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = grupo,
                        onValueChange = {
                            if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                                grupo = it
                            }
                        },
                        label = { Text("Grupo") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.width(150.dp).weight(1f)
                    )
                }

                item {
                    OutlinedTextField(
                        value = seccion,
                        onValueChange = {
                            if (it.length <= 1 && it.all { char -> char.isLetter() }) {
                                seccion = it
                            }
                        },
                        label = { Text("Sección") },
                        modifier = Modifier.width(150.dp).weight(1f)
                    )
                }
                item {
                    OutlinedTextField(
                        value = eliminacion_matricula,
                        onValueChange = {
                            if (Alumno.alumno.matriculaIsValid(it)){
                                eliminacion_matricula = it
                            }
                        },
                        label = {Text("Eliminacion ")},
                        modifier = Modifier.width(150.dp).weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Row() {
                        Button(
                            onClick = {
                                  elliminacion_alumno =   lista_alumnos.find { it.matricula == eliminacion_matricula }
                                if (elliminacion_alumno == null){
                                    mensaje_para_nubs= "Alumno no encontrado"
                                }

                                lista_alumnos.remove(elliminacion_alumno)

                            },
                            modifier = Modifier.size(30.dp),
                            shape = RectangleShape,
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar_matri"
                            )
                        }
                    }
                }

            }

            // Botón Guardar
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 23.dp, vertical = 8.dp),
                onClick = {
                    mensaje_para_nubs = ""
                    alumno_guardado = ""


                    // validaciones por si estan vacios
                    if (matricula.isBlank() || nombre.isBlank() || edad.isBlank() || grupo.isBlank() || seccion.isBlank()) {
                        mensaje_para_nubs = "Llena todos los campos vacíos"
                    }

                    //modo de edicion
                    else if (alumno_editando != null){
                        val alumno_actualizado = Alumno.alumno(
                            nombre = nombre,
                            matricula = matricula,
                            edad = edad.toInt(),
                            Grupo = grupo.toInt(),
                            Seccion = seccion.first().uppercaseChar()
                        )


                        val index = lista_alumnos.indexOf(alumno_editando)
                        if (index != -1){
                            lista_alumnos[index] = alumno_actualizado
                            alumno_guardado = "Alumno actualizado"
                        }

                        //aca para que no se quede guardado los datos del otro alumno
                        alumno_editando = null
                        matricula = ""
                        nombre = ""
                        edad =""
                        grupo = ""
                        seccion =""
                    }

                     else if (Alumno.alumno.matriculaExiste(matricula,lista_alumnos)){
                         mensaje_para_nubs="Error la matricula $matricula ya se encuentra registarada"
                    }
                     //registro de agregar
                     else {
                        val alumno_niu = Alumno.alumno(
                            nombre = nombre,
                            matricula = matricula,
                            edad = edad.toInt(),
                            Grupo = grupo.toInt(),
                            Seccion = seccion.first().uppercaseChar()
                        )
                        lista_alumnos.add(alumno_niu)
                        alumno_guardado = "¡Alumno agregado con éxito!"

                        // Limpiar campos del formulario
                        matricula = ""
                        nombre = ""
                        edad = ""
                        grupo = ""
                        seccion = ""
                    }
                }
            ) {
                Text(if (alumno_editando != null) "Actualizar alumno" else "Guardar alumno")
            }

            //
            if (alumno_guardado.isNotEmpty()) {
                Text(
                    text = alumno_guardado,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            if (mensaje_para_nubs.isNotEmpty()) {
                Text(
                    text = mensaje_para_nubs,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
            //Lsita de alumnos
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)

            //Aqui van los alumnos mostralos en cards para wue se vena kiuts
        ) {
            items(lista_alumnos) { alumno ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //info del cchavalr
                        Column {
                            Text(
                                text = alumno.nombre,
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "Matrícula: ${alumno.matricula} | Edad: ${alumno.edad}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Text(
                            text = "${alumno.Grupo}° ${alumno.Seccion}",
                            style = MaterialTheme.typography.labelLarge
                        )

                        //botones de editar alu y delete
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                           Button( onClick = {
                               lista_alumnos.remove(alumno)
                           } ,
                               modifier = Modifier.size(30.dp),
                               shape = RectangleShape,
                               contentPadding = PaddingValues(0.dp)
                               )
                                    {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Borrar alumno"
                               )
                           }
                            Button(
                                onClick = {
                                    nombre = alumno.nombre
                                    matricula = alumno.matricula
                                    edad = alumno.edad.toString()
                                    grupo = alumno.Grupo.toString()
                                    seccion = alumno.Seccion.toString()
                                    alumno_editando = alumno
                                },
                                modifier = Modifier.size(30.dp),
                                shape = RectangleShape,
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Editar alumno"
                                )
                            }


                        }

                    }
                }
            }
        }
    }
}







//
////ejemplos cracks
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AppNavigation() {
//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = "inicio"
//    ){
//        composable ("inicio" ){
//            PantallaInicio(
//                onNavigationOne={
//                    navController.navigate("segunda")
//                },
//                onNavigateTwo={
//                    navController.navigate("tercera")
//                },
//                onNavigateExterna={
//                    navController.navigate("externa")
//                }
//            )
//        }
//
//        composable("tercera") {
//            PantallaSegunda(
//                onHome={
//                    navController.navigate("inicio")
//                }
//            )
//        }
//
//        composable("tercera") {
//            PantallaTercera(
//                onHome = {
//                    navController.navigate("inicio")
//                }
//            )
//        }
//
//        composable("externa") {
//            ExternalComposable(
//                onHome = {
//                    navController.navigate("inicio")
//                }
//            )
//        }
//    }
//}
//
//
//@Composable
//fun PantallaInicio(
//    onNavigationOne: () -> Unit,
//    onNavigateTwo: () -> Unit,
//    onNavigateExterna: () -> Unit)
//{
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text("Pantalla de Inicio")
//
//        Button(onClick = onNavigationOne) {
//            Text("Ir a segunda pantalla")
//        }
//
//        Button(onClick = onNavigateTwo) {
//            Text("Ir a tercera pantalla")
//        }
//
//        Button(onClick = onNavigateExterna) {
//            Text("Ir a exerna pantalla")
//        }
//    }
//}
//
//
//@Composable
//fun PantallaSegunda(onHome: () -> Unit) {
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text("Tercera Pantalla")
//
//        Button(onClick = onHome) {
//            Text("Inicio")
//        }
//    }
//}
//
//
//@Composable
//fun PantallaTercera(onHome: () -> Unit) {
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text("Tercera Pantalla")
//
//        Button(onClick = onHome) {
//            Text("Inicio")
//        }
//    }
//}
//
//
