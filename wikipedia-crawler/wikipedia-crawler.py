import wikipedia
import requests
import os
import re

start_cat_id = 4423397

wiki_api_url = "https://en.wikipedia.org/w/api.php"

output_file_path = "wikioutput.txt"

params_template = {
    'action': "query",
    'list': "categorymembers",
    'cmlimit': "max",
    'format': "json"
}


def build_params(cat_id, req_type):
    return {
        **params_template,
        'cmpageid': cat_id,
        'cmtype': req_type
    }


def perform_get_request(cat_id, req_type, param_builder):
    result = requests.get(wiki_api_url, param_builder(cat_id, req_type)).json()
    try:
        return result['query']['categorymembers']
    except KeyError:
        return []


def bfs_traverse(cat_id, on_pages_list, visited):
    try:
        visited.index(cat_id)
    except ValueError:
        visited.append(cat_id)
        clear_screen()
        print(len(visited))
        on_pages_list(perform_get_request(cat_id, "page", build_params), visited)
        [bfs_traverse(sub_cat_id['pageid'], on_pages_list, visited)
        for sub_cat_id in perform_get_request(cat_id, "subcat", build_params)]


if os.path.exists(output_file_path):
    os.remove(output_file_path)

f = open(output_file_path, "a", encoding='utf-8')

def clear_screen():
    os.system('cls')

def write_to_file_formatted(text):
    text = re.sub('== See also ==', '', text)
    text = re.sub('== References ==', '', text)
    text = re.sub('== External links ==', '', text)
    text = re.sub('==+ ', '', text)
    text = re.sub(' ==+', '', text)
    f.write(text)

def on_pages_list(list, visited):
    [on_page(page, visited) for page in list]
    #[write_to_file_formatted(wikipedia.page(pageid=elem['pageid'], redirect=False).content) for elem in list]

def on_page(page, visited):
    try:
      visited.index(page['pageid'])
    except ValueError:
      visited.append(page['pageid'])
      clear_screen()
      print(len(visited))
      write_to_file_formatted(wikipedia.page(pageid=page['pageid'], redirect=False).content)
    

bfs_traverse(start_cat_id, on_pages_list, [])

print("DONE")
