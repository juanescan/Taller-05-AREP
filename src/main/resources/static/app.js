const apiUrl = '/api/properties';
let editingId = null; // ✅ Guardará el id del registro en edición

async function listProperties() {
  const res = await fetch(apiUrl);
  if (!res.ok) { showMsg('Error al obtener datos'); return; }

  const props = await res.json();
  const ul = document.getElementById('propertiesList');
  ul.innerHTML = '';

  props.forEach(p => {
    const li = document.createElement('li');
    li.innerHTML = `
      ${p.address} — ${p.price} — ${p.size}
      <button onclick="view(${p.id})">Ver</button>
      <button onclick="edit(${p.id})">Editar</button>
      <button onclick="delProp(${p.id})">Borrar</button>
    `;
    ul.appendChild(li);
  });
}

document.getElementById('createForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const fd = new FormData(e.target);
  const body = {
    address: fd.get('address'),
    price: parseFloat(fd.get('price')),
    size: parseFloat(fd.get('size')),
    description: fd.get('description')
  };

  // 🔹 Si estamos editando, usamos PUT, si no POST
  const method = editingId ? 'PUT' : 'POST';
  const url = editingId ? `${apiUrl}/${editingId}` : apiUrl;

  const res = await fetch(url, {
    method,
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body)
  });

  if (res.ok) {
    showMsg(editingId ? 'Actualizado' : 'Creado');
    e.target.reset();
    editingId = null;                          // ✅ Terminar modo edición
    document.getElementById('submitBtn').textContent = 'Crear'; // Volver a texto normal
    listProperties();
  } else {
    showMsg('Error al guardar');
  }
});

async function delProp(id) {
  if (!confirm('¿Seguro que deseas borrar este registro?')) return;
  const res = await fetch(`${apiUrl}/${id}`, { method: 'DELETE' });
  if (res.ok) { showMsg('Eliminado'); listProperties(); }
  else showMsg('Error al eliminar');
}

async function edit(id) {
  // ✅ Traer datos para rellenar el formulario
  const res = await fetch(`${apiUrl}/${id}`);
  if (!res.ok) { showMsg('No se pudo obtener el registro'); return; }

  const p = await res.json();
  document.getElementById('address').value = p.address;
  document.getElementById('price').value = p.price;
  document.getElementById('size').value = p.size;
  document.getElementById('description').value = p.description || '';

  editingId = id;
  document.getElementById('submitBtn').textContent = 'Actualizar';
  showMsg(`Editando registro #${id}`);
}

function view(id) {
  alert('Implementa ver detalle ' + id);
}

function showMsg(m) {
  document.getElementById('messages').innerText = m;
}

window.onload = listProperties;