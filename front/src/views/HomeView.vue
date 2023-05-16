<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";

const posts = ref([]);

  axios.get("/api/posts?page=1&size=5").then((response) => {
    response.data.forEach((r: any) => {
      posts.value.push(r);
    });
  });
</script>

<template>
  <ul>
    <li v-for="post in posts" :key="post.id">
      <div>
        {{ post.title }}
      </div>
      <div>
        {{ post.content }}
      </div>
    </li>
  </ul>
</template>

<style>
  li {
    margin-bottom : 0.5em;
  }
  li:last-child {
    margin-bottom : 0em;
  }
</style>