<script setup lang="ts">

import {useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import axios from "axios";

const router = useRouter();
const post = ref({});
const props = defineProps({
  postId : {
    type : [String, Number],
    required : true
  }
});

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
    post.value = response.data;
  });
});

const edit = function() {
  axios.patch(`/api/posts/${props.postId}`, post.value).then((response)=>{
    router.replace({name:"home"});
  });
}

</script>

<template>
  <div>
    <el-input v-model="post.title" placeholder="제목을 입력해주세요."/>
  </div>
  <div class="mt-2">
    <el-input v-model="post.content" type="textarea" rows="15">
    </el-input>
  </div>
  <div class="mt-2 d-flex justify-content-end">
    <el-button type="warning" @click="edit()">수정완료</el-button>
  </div>
</template>

<style>
</style>