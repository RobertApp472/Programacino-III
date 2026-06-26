const API = "http://localhost:8080/api/vehiculos";

/* ---------------- CREAR ---------------- */
function crearVehiculo() {
    const vehiculo = {
        modelo: document.getElementById("modelo").value,
        categoria: document.getElementById("categoria").value,
        descripcion: document.getElementById("descripcion").value,
        precioPorDia: parseFloat(document.getElementById("precio").value),
        unidadesDisponibles: parseInt(document.getElementById("unidades").value)
    };

    fetch(API, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(vehiculo)
    })
    .then(res => res.json())
    .then(() => {
        alert("Vehículo creado correctamente");
        listarVehiculos();
    });
}

/* ---------------- LISTAR ---------------- */
function listarVehiculos() {
    fetch(API)
        .then(res => res.json())
        .then(data => {
            const lista = document.getElementById("lista");
            lista.innerHTML = "";

            data.forEach(v => {
                lista.innerHTML += `
                    <div class="item">
                        <b>${v.modelo}</b><br>
                        Categoría: ${v.categoria}<br>
                        Precio: $${v.precioPorDia}<br>
                        Unidades: ${v.unidadesDisponibles}<br>
                        <button onclick="eliminar(${v.id})">Eliminar</button>
                        <button onclick="cargarParaEditar(${v.id}, '${v.modelo}', '${v.categoria}', '${v.descripcion}', ${v.precioPorDia}, ${v.unidadesDisponibles})">Editar</button>
                    </div>
                `;
            });
        });
}

/* ---------------- BUSCAR POR ID ---------------- */
function buscarPorId() {
    const id = document.getElementById("buscarId").value;

    fetch(`${API}/${id}`)
        .then(res => res.json())
        .then(v => {
            document.getElementById("resultadoId").innerHTML = `
                <div class="item">
                    <b>${v.modelo}</b><br>
                    Categoría: ${v.categoria}<br>
                    Precio: $${v.precioPorDia}<br>
                    Unidades: ${v.unidadesDisponibles}
                </div>
            `;
        })
        .catch(() => {
            document.getElementById("resultadoId").innerHTML = "No encontrado";
        });
}

/* ---------------- ELIMINAR ---------------- */
function eliminar(id) {
    fetch(`${API}/${id}`, { method: "DELETE" })
        .then(() => {
            alert("Eliminado");
            listarVehiculos();
        });
}

/* ---------------- EDITAR ---------------- */
function cargarParaEditar(id, modelo, categoria, descripcion, precio, unidades) {
    const nuevoModelo = prompt("Nuevo modelo:", modelo);
    const nuevaCategoria = prompt("Nueva categoría:", categoria);
    const nuevaDescripcion = prompt("Nueva descripción:", descripcion);
    const nuevoPrecio = prompt("Nuevo precio:", precio);
    const nuevasUnidades = prompt("Nuevas unidades:", unidades);

    const vehiculo = {
        modelo: nuevoModelo,
        categoria: nuevaCategoria,
        descripcion: nuevaDescripcion,
        precioPorDia: parseFloat(nuevoPrecio),
        unidadesDisponibles: parseInt(nuevasUnidades)
    };

    fetch(`${API}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(vehiculo)
    })
    .then(() => {
        alert("Actualizado");
        listarVehiculos();
    });
}