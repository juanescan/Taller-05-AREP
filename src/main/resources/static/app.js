const apiUrl = '/api/properties'; 

async function listProperties(){
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


document.getElementById('createForm').addEventListener('submit', async (e)=>{
  e.preventDefault();
  const fd = new FormData(e.target);
  const body = {
    address: fd.get('address'),
    price: parseFloat(fd.get('price')),
    size: parseFloat(fd.get('size')),
    description: fd.get('description')
  };

  const res = await fetch(apiUrl, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body)
  });

  if (res.ok){
    showMsg('Creado');
    e.target.reset();
    listProperties();
  } else {
    showMsg('Error al crear');
  }
});


async function delProp(id){
  if (!confirm('¿Seguro que deseas borrar este registro?')) return;

  const res = await fetch(`${apiUrl}/${id}`, { method: 'DELETE' });

  if (res.ok){
    showMsg('Eliminado');
    listProperties();
  } else {
    showMsg('Error al eliminar');
  }
}


function showMsg(m){
  document.getElementById('messages').innerText = m;
}


function view(id){ alert('Implementa ver detalle ' + id); }
function edit(id){ alert('Implementa editar ' + id); }


window.onload = listProperties;