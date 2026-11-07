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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.practica5cr.`data-dao`.Alumno
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practica5cr.dataBase.AppDatabase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Practica5CRTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                }
            }
        }
    }
}





//@composable
//fun registro_alumno() {
//    var elliminacion_alumno by remember { mutablestateof<alumno.alumno?>(null) }
//    var eliminacion_matricula by remember { mutablestateof("") }
//    val lista_alumnos = remember { mutablestatelistof<alumno.alumno>() }
//    var alumno_editando by remember { mutablestateof<alumno.alumno?>(null) }
//    var alumno_guardado by remember { mutablestateof("") }
//    var mensaje_para_nubs by remember { mutablestateof("") }
//    var matricula by remember { mutablestateof("") }
//    var nombre by remember { mutablestateof("") }
//    var edad by remember { mutablestateof("") }
//    var grupo by remember { mutablestateof("") }
//    var seccion by remember { mutablestateof("") }
//
//    column(
//        modifier = modifier
//            .fillmaxsize()
//            .padding(top = 20.dp)
//
//    ) {
//        column(
//            modifier = modifier.fillmaxwidth(),
//            horizontalalignment = alignment.centerhorizontally
//        ) {
//            // matrícula
//            outlinedtextfield(
//                value = matricula,
//                onvaluechange = {
//                    if (alumno.alumno.matriculaisvalid(it)) {
//                        matricula = it
//                    }
//                },
//                label = { text("matrícula") },
//                readonly = alumno_editando != null,
//                keyboardoptions = keyboardoptions(keyboardtype = keyboardtype.number)
//            )
//
//            // nombre (permite letras y espacios)
//            outlinedtextfield(
//                value = nombre,
//                onvaluechange = {
//                    if (it.all { char -> char.isletter() || char.iswhitespace()  }) {
//                        nombre= it.trim()
//                    }
//                },
//                label = { text("nombre") }
//            )
//
//            // edad
//            outlinedtextfield(
//                value = edad,
//                onvaluechange = {
//                    if (alumno.alumno.edadisvalid(it) || it.isempty()) {
//                        edad = it
//                    }
//                },
//                label = { text("edad") },
//                keyboardoptions = keyboardoptions(keyboardtype = keyboardtype.number)
//            )
//
//            // grupo y sección
//            lazyrow (
//                modifier = modifier
//                    .fillmaxwidth()
//                    .padding(16.dp),
//                horizontalarrangement = arrangement.spacedby(10.dp)
//            ) {
//                item {
//                    outlinedtextfield(
//                        value = grupo,
//                        onvaluechange = {
//                            if (it.length <= 1 && it.all { char -> char.isdigit() }) {
//                                grupo = it
//                            }
//                        },
//                        label = { text("grupo") },
//                        keyboardoptions = keyboardoptions(keyboardtype = keyboardtype.number),
//                        modifier = modifier.width(150.dp).weight(1f)
//                    )
//                }
//
//                item {
//                    outlinedtextfield(
//                        value = seccion,
//                        onvaluechange = {
//                            if (it.length <= 1 && it.all { char -> char.isletter() }) {
//                                seccion = it
//                            }
//                        },
//                        label = { text("sección") },
//                        modifier = modifier.width(150.dp).weight(1f)
//                    )
//                }
//                item {
//                    outlinedtextfield(
//                        value = eliminacion_matricula,
//                        onvaluechange = {
//                            if (alumno.alumno.matriculaisvalid(it)){
//                                eliminacion_matricula = it
//                            }
//                        },
//                        label = {text("eliminacion ")},
//                        modifier = modifier.width(150.dp).weight(1f),
//                        keyboardoptions = keyboardoptions(keyboardtype = keyboardtype.number)
//                    )
//                    row() {
//                        button(
//                            onclick = {
//                                  elliminacion_alumno =   lista_alumnos.find { it.matricula == eliminacion_matricula }
//                                if (elliminacion_alumno == null){
//                                    mensaje_para_nubs= "alumno no encontrado"
//                                }
//
//                                lista_alumnos.remove(elliminacion_alumno)
//
//                            },
//                            modifier = modifier.size(30.dp),
//                            shape = rectangleshape,
//                            contentpadding = paddingvalues(0.dp)
//                        ) {
//                            icon(
//                                imagevector = icons.default.delete,
//                                contentdescription = "borrar_matri"
//                            )
//                        }
//                    }
//                }
//
//            }
//
//            // botón guardar
//            button(
//                modifier = modifier
//                    .fillmaxwidth()
//                    .padding(horizontal = 23.dp, vertical = 8.dp),
//                onclick = {
//                    mensaje_para_nubs = ""
//                    alumno_guardado = ""
//
//
//                    // validaciones por si estan vacios
//                    if (matricula.isblank() || nombre.isblank() || edad.isblank() || grupo.isblank() || seccion.isblank()) {
//                        mensaje_para_nubs = "llena todos los campos vacíos"
//                    }
//
//                    //modo de edicion
//                    else if (alumno_editando != null){
//                        val alumno_actualizado = alumno.alumno(
//                            nombre = nombre,
//                            matricula = matricula,
//                            edad = edad.toint(),
//                            grupo = grupo.toint(),
//                            seccion = seccion.first().uppercasechar()
//                        )
//
//
//                        val index = lista_alumnos.indexof(alumno_editando)
//                        if (index != -1){
//                            lista_alumnos[index] = alumno_actualizado
//                            alumno_guardado = "alumno actualizado"
//                        }
//
//                        //aca para que no se quede guardado los datos del otro alumno
//                        alumno_editando = null
//                        matricula = ""
//                        nombre = ""
//                        edad =""
//                        grupo = ""
//                        seccion =""
//                    }
//
//                     else if (alumno.alumno.matriculaexiste(matricula,lista_alumnos)){
//                         mensaje_para_nubs="error la matricula $matricula ya se encuentra registarada"
//                    }
//                     //registro de agregar
//                     else {
//                        val alumno_niu = alumno.alumno(
//                            nombre = nombre,
//                            matricula = matricula,
//                            edad = edad.toint(),
//                            grupo = grupo.toint(),
//                            seccion = seccion.first().uppercasechar()
//                        )
//                        lista_alumnos.add(alumno_niu)
//                        alumno_guardado = "¡alumno agregado con éxito!"
//
//                        // limpiar campos del formulario
//                        matricula = ""
//                        nombre = ""
//                        edad = ""
//                        grupo = ""
//                        seccion = ""
//                    }
//                }
//            ) {
//                text(if (alumno_editando != null) "actualizar alumno" else "guardar alumno")
//            }
//
//            //
//            if (alumno_guardado.isnotempty()) {
//                text(
//                    text = alumno_guardado,
//                    color = materialtheme.colorscheme.primary,
//                    modifier = modifier.padding(vertical = 8.dp)
//                )
//            }
//
//            if (mensaje_para_nubs.isnotempty()) {
//                text(
//                    text = mensaje_para_nubs,
//                    color = materialtheme.colorscheme.error,
//                    modifier = modifier.padding(vertical = 8.dp)
//                )
//            }
//        }
//            //lsita de alumnos
//        lazycolumn(
//            modifier = modifier.fillmaxwidth(),
//            contentpadding = paddingvalues(horizontal = 16.dp, vertical = 8.dp),
//            verticalarrangement = arrangement.spacedby(8.dp)
//
//            //aqui van los alumnos mostralos en cards para wue se vena kiuts
//        ) {
//            items(lista_alumnos) { alumno ->
//                card(
//                    modifier = modifier.fillmaxwidth(),
//                    colors = carddefaults.cardcolors(
//                        containercolor = materialtheme.colorscheme.surfacevariant
//                    )
//                ) {
//                    row(
//                        modifier = modifier
//                            .fillmaxwidth()
//                            .padding(12.dp),
//                        horizontalarrangement = arrangement.spacebetween,
//                        verticalalignment = alignment.centervertically
//                    ) {
//                        //info del cchavalr
//                        column {
//                            text(
//                                text = alumno.nombre,
//                                style = materialtheme.typography.titlesmall
//                            )
//                            text(
//                                text = "matrícula: ${alumno.matricula} | edad: ${alumno.edad}",
//                                style = materialtheme.typography.bodysmall
//                            )
//                        }
//                        text(
//                            text = "${alumno.grupo}° ${alumno.seccion}",
//                            style = materialtheme.typography.labellarge
//                        )
//
//                        //botones de editar alu y delete
//                        row(
//                            horizontalarrangement = arrangement.spacedby(4.dp)
//                        ) {
//                           button( onclick = {
//                               lista_alumnos.remove(alumno)
//                           } ,
//                               modifier = modifier.size(30.dp),
//                               shape = rectangleshape,
//                               contentpadding = paddingvalues(0.dp)
//                               )
//                                    {
//                                        icon(
//                                            imagevector = icons.default.delete,
//                                            contentdescription = "borrar alumno"
//                               )
//                           }
//                            button(
//                                onclick = {
//                                    nombre = alumno.nombre
//                                    matricula = alumno.matricula
//                                    edad = alumno.edad.tostring()
//                                    grupo = alumno.grupo.tostring()
//                                    seccion = alumno.seccion.tostring()
//                                    alumno_editando = alumno
//                                },
//                                modifier = modifier.size(30.dp),
//                                shape = rectangleshape,
//                                contentpadding = paddingvalues(0.dp)
//                            ) {
//                                icon(
//                                    imagevector = icons.default.edit,
//                                    contentdescription = "editar alumno"
//                                )
//                            }
//
//
//                        }
//
//                    }
//                }
//            }
//        }
//    }
//}





//el deste de la desta no se crea nav host

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    //el dao dao el dao dao
    val dao = remember { AppDatabase.getDatabase(context).interAlumnoDao() }
    val scope = rememberCoroutineScope ()

    var alummnoEditar by remember { mutableStateOf<Alumno?>(null) }
    NavHost(
        navController = navController,
        startDestination = "tabla_alumnos"

    ){
        composable ("tabla_alumnos" ){
            var  listaAlumnos by remember { mutableStateListOf<List<Alumno>>(emptyList()) }

            LaunchedEffect(Unit) {
                listaAlumnos = dao.getAlumnos()
            }

            PantallaAlumnos(
                listaAlumnos = listaAlumnos,
                onNavigationOne = {
                    alummnoEditar = null
                    navController.navigate("agg_edit")
            },
                onNavigationTwo = {
                    navController.navigate("delete")
                },


            )

        }

        composable ("agg_edit") {
            PantallaAggEdit(
                onHome = {
                    navController.navigate("tabla_alumnos")
                }
            )
        }

        composable("delete") {
            PantallaDelete(
                onHome = {
                    navController.navigate("tabla_alumnos")
                }
            )
        }




    }

}

//aca van las pantashas

@Composable
fun PantallaAlumnos(
    onNavigationOne : () -> Unit,
    onNavigationTwo: () -> Unit,
    onEditarAlumno: (Alumno) -> Unit,
    onEliminarBoton: (Alumno) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = "Registro de Alumnos",
            color = Color.Blue)
    }
    Spacer(modifier = Modifier.height(12.dp))

    LazyColumn(
        modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listaAlumnos, Key = {it.matricula}) { alumno ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = alumno.nombre,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Matricula: ${alumno.matricula} | Edad ${alumno.edad}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Grupo : ${alumno.grupo}° ${alumno.seccion}",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Button(onClick =  {
                            onEliminarBoton(alumno) },
                            modifier = Modifier.size(36.dp),
                            shape = RectangleShape,
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                        Button(onClick =  {
                            onEditarAlumno(alumno) },
                            modifier = Modifier.size(36.dp),
                            shape = RectangleShape,
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = onNavigationOne) {
                    Text("NIU")
                }
                Button(onClick = onNavigationTwo) {
                    Text("DELETEA")
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
