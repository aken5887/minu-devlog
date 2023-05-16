<script setup lang="ts">
import {onMounted, ref} from "vue";
import axios from "axios";

const props = defineProps({
  postId : {
    type : [Number, String],
    required : true
  }
});

const post = ref({});

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
      post.value = response.data;
    });
});
</script>

<template>
  <h2>제목 : {{ post.title }}</h2>
  <div>내용 : {{ post.content }}</div>
</template>