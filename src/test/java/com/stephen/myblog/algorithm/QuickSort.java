package com.stephen.myblog.algorithm;

public class QuickSort {

    static int a []={231,3,54,6,7,21,1,788};

    public static void main(String args[]){
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]+",");
        }
      quicksort(1,7);
        for(int i=0;i<a.length;i++){
            System.out.print(a[i]+",");
        }
    }


    public static void quicksort(int left,int right){
        int i,j,t,temp;
        temp=a[left]; //temp中存的就是基准数
        i=left;
        j=right;
        while(i!=j){
            while(a[j]>=temp && i<j){
                j--;
            }
            while (a[i]<temp&& i<j){
                i++;
            }
            t=a[i];
            a[i]=a[j];
            a[j]=t;
        }
//        a[left]=a[i];
//        a[i]=temp;
        quicksort(left,i-1);
        quicksort(i+1,right);
    }
}
