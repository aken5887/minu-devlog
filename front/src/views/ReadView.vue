<script setup lang="ts">
import {onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const props = defineProps({
  postId : {
    type : [Number, String],
    required : true
  }
});

const post = ref({});
const router = useRouter();

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
      post.value = response.data;
    });
});

const moveToEdit = function() {
  router.push({name : 'edit', params : { postId : props.postId }});
}
</script>

<template>
  <h2>제목 : {{ post.title }}</h2>
  <div>내용 : {{ post.content }}</div>
  <el-button type="warning" @click="moveToEdit()">수정</el-button>
</template>