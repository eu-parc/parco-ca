@Grab('org.apache.commons:commons-csv:1.2')
import org.apache.commons.csv.CSVParser;
import static org.apache.commons.csv.CSVFormat.*;

// Step 1: identify classes bit cut off
// identify the prefixes needed (maybe that's step 1 actually)
// step 2: insert the mappings, delete columns with no mappings.
// convert to tsv?

def MAP = [
  'Identifier': ['Ontology ID', 'ID'],
  'skos:prefLabel@en(id="label")': ['Label', 'LABEL'],
  'skos:altLabel(separator=",")': ['alt label', 'A alternative term SPLIT=,'],
  'skos:notation': ['skos:notation', 'A skos:notation'],
  'skos:definition@en': ['definition', 'A IAO:0000115'],
  'dct:source(separator=",")': ['definition source', 'A definition source split=,'],
  'skos:broader(separator=",")':['parent', 'C % SPLIT=,'],
  'rdf:type': ['rdf:type', 'A rdf:type'],
  'owlsameAs': ['same as', 'A owl:sameAs'],
  'skos:exactMatch(separator=",")': ['exact synonym', 'A skos:exactMatch split=,'],
  'skos:closeMatch(separator=",")': ['close synonym', 'A skos:closeMatch split=,'],
  'skos:broadMatch(separator=",")': ['broad synonym', 'A skos:broadmatch split=,'],
  'iop:hasProperty': ['iop:hasProperty', 'A iop:hasProperty'],
  'iop:hasObjectOfInterest': ['iop:hasObjectofInterest', 'A iop:hasObjectOfInterest'],
  'iop:hasMatrix': ['iop:hasMatrix', 'A iop:hasMatrix'],
  'iop:hasContextObject(separator=",")': ['iop:hasContextObject', 'A iop:hasContextObject'],
  'iop:hasConstraint(separator=",")': ['iop:hasConstraint', 'A iop:hasConstraint'],
  'puv:statistic(separator=",")': ['puv:statistic', 'A puv:statistic'],
  'puv:usesMethod(separator=",")': ['puv:usesMethod', 'A puv:usesMethod'],
  'sosa:madeBySensor(separator=",")': ['sosa:madeBySensor', 'A sosa:madeBySensor'],
  'puv:uom(separator=",")': ['puv:uom', 'A puv:uom sep=,'],
  'owl:deprecated^^xsd:boolean': ['deprecated', 'A owl:deprecated'],
  'skos:editorialNote@en': ['editor note', 'A rdfs:comment'],
  'rdf:type2': ['rdf:type2', 'A rdf:type'],
  'dct:created^^xsd:date': ['created', 'A dct:created'],
  'dct:modified^^xsd:date': ['modified', 'A dct:modified'],
  'dct:creator(separator=",")': ['creator', 'A dct:creator'],
  'dct:contributor(separator=",")': ['contributor', 'A dct:contributor split=,']
]

def map = [:]
def out = []
new File(args[0]).withReader { reader ->
  CSVParser csv = new CSVParser(reader, DEFAULT.withHeader())
  def heading = csv.getHeaderMap().keySet().toList()
  out << heading.join('\t')
  out << heading.collect { 
println MAP[it][1]
    MAP[it][1] }.join('\t')
  for(record in csv.iterator()) {
    out << record.values().join('\t')
  }
}

new File('../templates/classes.tsv').text = out.join('\n')
