<template>
  <div class="wrapper">
    <ProgressSpinner />
  </div>
</template>

<script setup>
import ProgressSpinner from 'primevue/progressspinner';
</script>

<style lang="css" scoped>
.wrapper {
  position: absolute;
  left: 50%;
  top: 50%;
  margin: -100px;
  width: 200px;
  height: 200px;
  background-color: transparent;
  border: none;
}
</style>  