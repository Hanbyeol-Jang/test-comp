from pymongo import MongoClient
from collections import defaultdict
import pandas as pd
import numpy as np
import surprise
from surprise import SVD
from surprise import Dataset
from surprise.model_selection import cross_validate
from surprise import Reader



def get_top_n(predictions,store_addr):
    # First map the predictions to each user.
    top_n = list()
    dic = {}
    for uid, iid, true_r, est, _ in predictions:
        store = store_addr.get(iid)
        dic1 = {"Uid":uid,"Rid":iid, "rating":est,"name":store[0],"address":store[1],"tel":store[2],"category":store[3],"mainMn":store[4],"price":store[5]
        ,"menu":store[6],"opngTm":store[7],"img":store[10],"tags":store[11]}
        top_n.append(dic1)
        
    # for uid, user_ratings in top_n.items():
    #     if uid==meeting_id:
    #         for user_rating in user_ratings:
    #             dic[user_rating[0]] =user_rating[1]
    return top_n

def testreview():
    df1 = pd.DataFrame(my_client['mimi']['reviewdata'].find())
    df2 = pd.DataFrame(my_client['mimi']['review'].find())
    df1 = df1[["resId","resName","userId","rating","review"]]
    df2 = df2[["resId","resName","userId","rating","review"]]

    print(df2)
    df = pd.concat([df1,df2],sort=True)
    print(df)
    store_df = pd.DataFrame(my_client['mimi']['store'].find())
    store_addr = {}
    store = store_df.values.tolist()
    for s in store:
        store_addr[s[0]] = s[1:]


    # Load the dataset (download it if needed)
    reader = Reader(rating_scale=(0.0, 5.0))
    data = Dataset.load_from_df(df[["userId","resId","rating"]],reader)
    trainset = data.build_full_trainset()
    algo = SVD()
    algo.fit(trainset)
    # Than predict ratings for all pairs (u, i) that are NOT in the training set.
    # testset = trainset.build_full_trainset()
    testset = trainset.build_anti_testset()
    predictions = algo.test(testset)
    top_n = get_top_n(predictions,store_addr)
    # Print the recommended items for each user
    
    # recom_qs = pd.DataFrame.my_client['mimi']['recommand'].find("Uid" : mid)
    my_client['mimi']['recommand'].remove()
    
    x = my_client['mimi']['recommand'].insert_many(top_n)
    

    # for recom in recom_qs:
    #     recom.rating = top_n.get(int(recom.res_id))
    #     recom.save()

# my_client = MongoClient("mongodb://k3b106.p.ssafy.io:27017")
my_client = MongoClient("mongodb://SSAFYMONGO:mongoadmin106@k3b106.p.ssafy.io:27017/?authSource=admin&readPreference=primary&appname=MongoDB%20Compass&ssl=false")
print(my_client.list_database_names())

t = my_client['mimi']['store'].find()
test_df  = pd.DataFrame(t)
testreview()