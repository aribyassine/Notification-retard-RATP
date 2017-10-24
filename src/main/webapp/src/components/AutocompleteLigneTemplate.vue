<template>
  <div>
    <b><span>{{item.name}}</span></b>
    <abbr>{{item.directions}}</abbr>
  </div>
</template>

<script>
  export default {
    props: { item: { required: true } }
  }
</script>
