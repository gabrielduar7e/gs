(function(){
  const API = 'http://localhost:8081/api';
  const els = {
    loginForm: document.getElementById('login-form'),
    loginResult: document.getElementById('login-result'),
    userInfo: document.getElementById('user-info'),
    btnLogout: document.getElementById('btn-logout'),
    btnUsuarios: document.getElementById('btn-load-usuarios'),
    usuariosResult: document.getElementById('usuarios-result'),
    btnCompetencias: document.getElementById('btn-load-competencias'),
    competenciasResult: document.getElementById('competencias-result'),
    competenciaForm: document.getElementById('competencia-form'),
    competenciaResult: document.getElementById('competencia-result'),
    recUsuario: document.getElementById('r-usuario'),
    btnRecMock: document.getElementById('btn-rec-mock'),
    recMockResult: document.getElementById('rec-mock-result'),
    recUsuarioIa: document.getElementById('r-usuario-ia'),
    btnRecIa: document.getElementById('btn-rec-ia'),
    recIaResult: document.getElementById('rec-ia-result'),
    mqUsuario: document.getElementById('mq-usuario'),
    btnMqPub: document.getElementById('btn-mq-publicar'),
    mqResult: document.getElementById('mq-result')
  };

  function getToken(){ return localStorage.getItem('jwt'); }
  function setToken(t){ localStorage.setItem('jwt', t); renderAuth(); }
  function clearToken(){ localStorage.removeItem('jwt'); renderAuth(); }

  function renderAuth(){
    const t = getToken();
    if(t){
      els.userInfo.textContent = 'Autenticado';
      els.btnLogout.style.display = 'inline-flex';
    } else {
      els.userInfo.textContent = 'Não autenticado';
      els.btnLogout.style.display = 'none';
    }
  }

  async function apiFetch(path, opts={}){
    const headers = opts.headers || {};
    headers['Content-Type'] = headers['Content-Type'] || 'application/json';
    const t = getToken();
    if(t){ headers['Authorization'] = 'Bearer ' + t; }
    const res = await fetch(API + path, { ...opts, headers });
    const text = await res.text();
    let data;
    try { data = text ? JSON.parse(text) : null; } catch { data = text; }
    if(!res.ok){
      throw { status: res.status, data };
    }
    return data;
  }

  // Login
  els.loginForm.addEventListener('submit', async (e)=>{
    e.preventDefault();
    els.loginResult.textContent='';
    const email = document.getElementById('email').value.trim();
    const senha = document.getElementById('senha').value;
    try{
      const resp = await apiFetch('/auth/login', { method:'POST', body: JSON.stringify({ email, senha }) });
      if(resp && resp.token){
        setToken(resp.token);
        els.loginResult.textContent = 'Login OK';
      } else {
        els.loginResult.textContent = 'Resposta inesperada.';
      }
    } catch(err){
      els.loginResult.textContent = 'Erro de login: ' + JSON.stringify(err.data || err);
    }
  });

  els.btnLogout.addEventListener('click', ()=>{ clearToken(); });

  // Listar usuários (GET público paginado)
  els.btnUsuarios.addEventListener('click', async ()=>{
    els.usuariosResult.textContent='';
    try{
      const data = await apiFetch('/usuarios?page=0&size=10&sort=nome,asc');
      els.usuariosResult.textContent = JSON.stringify(data, null, 2);
    }catch(err){
      els.usuariosResult.textContent = 'Erro: ' + JSON.stringify(err.data || err);
    }
  });

  // Listar competências (GET público paginado)
  els.btnCompetencias.addEventListener('click', async ()=>{
    els.competenciasResult.textContent='';
    try{
      const data = await apiFetch('/competencias?page=0&size=10&sort=nome,asc');
      els.competenciasResult.textContent = JSON.stringify(data, null, 2);
    }catch(err){
      els.competenciasResult.textContent = 'Erro: ' + JSON.stringify(err.data || err);
    }
  });

  // Criar competência (ADMIN)
  els.competenciaForm.addEventListener('submit', async (e)=>{
    e.preventDefault();
    els.competenciaResult.textContent='';
    const nome = document.getElementById('c-nome').value.trim();
    const descricao = document.getElementById('c-desc').value.trim();
    const categoria = document.getElementById('c-cat').value;
    try{
      const resp = await apiFetch('/competencias', { method:'POST', body: JSON.stringify({ nome, descricao, categoria }) });
      els.competenciaResult.textContent = 'Criada: ' + JSON.stringify(resp, null, 2);
      e.target.reset();
    }catch(err){
      els.competenciaResult.textContent = 'Erro ao criar: ' + JSON.stringify(err.data || err);
    }
  });

  // Recomendações mock
  els.btnRecMock.addEventListener('click', async ()=>{
    els.recMockResult.textContent='';
    const id = parseInt(els.recUsuario.value || '1', 10);
    try{
      const data = await apiFetch(`/recomendacoes/${id}/gerar`);
      els.recMockResult.textContent = JSON.stringify(data, null, 2);
    }catch(err){
      els.recMockResult.textContent = 'Erro: ' + JSON.stringify(err.data || err);
    }
  });

  // Recomendações IA (com fallback)
  els.btnRecIa.addEventListener('click', async ()=>{
    els.recIaResult.textContent='';
    const id = parseInt(els.recUsuarioIa.value || '1', 10);
    try{
      const data = await apiFetch(`/recomendacoes/${id}/ai`);
      els.recIaResult.textContent = JSON.stringify(data, null, 2);
    }catch(err){
      els.recIaResult.textContent = 'Erro: ' + JSON.stringify(err.data || err);
    }
  });

  // Publicar na fila (RabbitMQ)
  els.btnMqPub.addEventListener('click', async ()=>{
    els.mqResult.textContent='';
    const id = parseInt(els.mqUsuario.value || '1', 10);
    try{
      await apiFetch(`/recomendacoes/${id}`, { method:'POST' });
      els.mqResult.textContent = 'Solicitação publicada (HTTP 202). Veja logs do consumidor.';
    }catch(err){
      els.mqResult.textContent = 'Erro: ' + JSON.stringify(err.data || err);
    }
  });

  renderAuth();
})();
