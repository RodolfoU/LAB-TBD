<template>
    <div>
    <h1>Todos los voluntarios</h1>
    <ul class="item-list">
      <li v-for="(item, index) in items" :key="index">
        {{item.nombre}}
      </li>
    </ul>
    <div v-if="items.length==0" class="empty-list">
      <em>No se han cargado los datos</em>
    </div>
  </div>
</template>
<script>
export default {
    //Función que contiene los datos del componente
    data(){
        return{
            //Lista de ítems a mostrar
            items:[]
        }
    },
    methods:{
        //Función asíncrona para consultar los datos
        getData: async function(){
            try {
                let response = await this.$http.get('/voluntarios');
                this.items  = response.data;
                console.log(response);
            } catch (error) {
                console.log('try catch');
            }
        }
    },
    //Función que se ejecuta al cargar el componente
    created:function(){
        this.getData();
    }
}
</script>